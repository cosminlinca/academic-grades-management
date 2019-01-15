package UI.Controller;

import Domain.NoteEntity;
import Domain.StudentiEntity;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class FilterController {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    private ObservableList<String> model = FXCollections.observableArrayList();
    private Integer grupa;
    private static final int N_COLS = 8;

    public void setFilterService(StudentService studentService, TemaService temaService, NotaService notaService, Integer grupa) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
        this.grupa = grupa;
        initModel();
    }

    @SuppressWarnings("Duplicates")
    private void initModel() {
        //add columns
        List<String> columnNames = Arrays.asList(new String[]{"Group " + this.grupa.toString(), "PatternsH", "ConsolaH", "NoteH", "XMLH", "JavaGuiH","FXMLH","C#H","SuplimentarH"});
        for(int i = 0; i<columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
            tableView.getColumns().add(column);
        }

        //add data
        List<String> data = Arrays.asList(new String[]{"","","","","","","","",""});

        Iterator<StudentiEntity> studentIterator = this.studentService.getStudentsIterator();
        while(studentIterator.hasNext()) {
            StudentiEntity auxStudent = studentIterator.next();
            if(auxStudent.getGrupa().equals(this.grupa) && auxStudent.isPrezent()) {
                //e din grupa x
                Iterator<NoteEntity> notaIterator = this.notaService.getGradesIterator();
                while(notaIterator.hasNext()) {
                    NoteEntity auxNota = notaIterator.next();
                    if(auxNota.getIdStudent().equals(auxStudent.getID())) {
                        //studentul y din grupa x are o nota la o tema
                        data.set(0,auxStudent.getNume());
                        data.set(auxNota.getIdTema(), auxNota.getValoare().toString());
                    }
                }
                tableView.getItems().add(FXCollections.observableArrayList(data));
                for(int i=0; i<9; i++)
                    data.set(i,"");
            }
        }
    }

    @FXML
    private TableView<ObservableList<String>> tableView;

    @FXML
    private void initialize() {

    }

}
