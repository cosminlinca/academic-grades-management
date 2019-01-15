package UI.Controller;

import Domain.MedieDTO;
import Domain.NoteEntity;
import Domain.StudentiEntity;
import Domain.TemeEntity;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StExamenController {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    private ObservableList<MedieDTO> model = FXCollections.observableArrayList();
    private List<MedieDTO> medii = new ArrayList<>();

    public void setMediiService(StudentService studentService, TemaService temaService, NotaService notaService) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;

        initModel();
    }

    @FXML
    public TableView<MedieDTO> tableView;
    @FXML
    public TableColumn<MedieDTO, String> nameColumn;
    @FXML
    public TableColumn<MedieDTO, Double> averageColumn;
    @FXML
    private Pagination pagination;
    private final static int rowPerPage = 10;

    @SuppressWarnings("Duplicates")
    private void initModel() {
        List<Integer> ponteri = new ArrayList<>();
        Iterator<TemeEntity> temeEntityIterator = this.temaService.getTemaIterator();
        while(temeEntityIterator.hasNext()) {
            TemeEntity temeEntity = temeEntityIterator.next();
            ponteri.add(temeEntity.getDeadline() - temeEntity.getPrimire());
        }

        Integer sum = ponteri.stream().mapToInt(Integer::intValue).sum();

        Iterator<StudentiEntity> studentiEntityIterator = this.studentService.getStudentsIterator();
        while(studentiEntityIterator.hasNext()) {
            StudentiEntity studentiEntity = studentiEntityIterator.next();
            Iterator<NoteEntity> noteEntityIterator = this.notaService.getGradesIterator();
            Double medie = 0d;
            while(noteEntityIterator.hasNext()) {
                NoteEntity noteEntity = noteEntityIterator.next();
                if(noteEntity.getIdStudent().equals(studentiEntity.getID())) {
                    //studentul x are o nota la o tema
                    TemeEntity temeEntity = this.temaService.getTemaById(noteEntity.getIdTema());
                    Integer pondere = temeEntity.getDeadline() - temeEntity.getPrimire();
                    medie = medie + (pondere * noteEntity.getValoare());
                }
            }
            medie = medie/sum;
            if(medie > 5)
                medii.add(new MedieDTO(studentiEntity.getNume(), studentiEntity.getGrupa(), medie));
        }

        medii.sort(Comparator.comparing(MedieDTO::getName));
        model.setAll(StreamSupport.stream(medii.spliterator(), false)
                .collect(Collectors.toList()));
    }

    @FXML
    public void initialize() {
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        //this.tableView.setItems(model);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowPerPage;
        int toIndex = Math.min(fromIndex + rowPerPage, model.size());
        if(fromIndex <= toIndex)
            tableView.setItems(FXCollections.observableArrayList(model.subList(fromIndex, toIndex)));
        else tableView.setItems(null);
        return tableView;
    }

    @FXML
    public void exportPDF() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)","*.pdf");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File retriver = fileChooser.showSaveDialog(null);
        if (retriver != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(retriver));
                document.open();
                Font blue = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL, BaseColor.BLUE);
                Chunk blueText = new Chunk("Studentii care intra in examen(sortati alfabetic)", blue);
                Paragraph paragraph = new Paragraph(blueText);
                paragraph.setSpacingAfter(15f);
                paragraph.setIndentationLeft(50f);
                document.add(paragraph);
                PdfPTable table = new PdfPTable(2);
                table.addCell("Name");
                table.addCell("Average");
                for (MedieDTO medieDTO: medii
                ) {
                    table.addCell(medieDTO.getName());
                    table.addCell(medieDTO.getAverage().toString());
                }

                document.add(table);
                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Done", "PDF generated!");
        }

    }

}
