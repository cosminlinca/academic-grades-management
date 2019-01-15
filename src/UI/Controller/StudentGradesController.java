package UI.Controller;

import Domain.*;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import UI.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StudentGradesController {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    private Integer stId;

    private ObservableList<NotaStDTO> model = FXCollections.observableArrayList();


    public void setStudentgService(StudentService studentgService, TemaService temaService, NotaService notaService, Integer id) {
        this.studentService = studentgService;
        this.temaService = temaService;
        this.notaService = notaService;
        this.stId = id;
        initModel();
    }

    @FXML
    private TableView<NotaStDTO> tableView;
    @FXML
    private TableColumn<NotaStDTO, String> taskColumn;
    @FXML
    private TableColumn<NotaStDTO, Integer> gradeColumn;
    @FXML
    private TableColumn<NotaStDTO, LocalDate> dateColumn;
    @FXML
    private Text nameText;
    @FXML
    private Text groupText;
    @FXML
    private Text emailText;
    @FXML
    private Pane pane;

    private void initModel() {
        List<NotaStDTO> rez = new ArrayList<>();
        Iterator<NoteEntity> noteEntities = this.notaService.getGradesIterator();
        while(noteEntities.hasNext()) {
            NoteEntity noteEntity = noteEntities.next();
            if(noteEntity.getIdStudent().equals(stId)) {
                String task = this.temaService.getTemaById(noteEntity.getIdTema()).getDescriere();
                rez.add(new NotaStDTO(task, noteEntity.getValoare(), noteEntity.getData()));
            }
        }

        model.setAll(StreamSupport.stream(rez.spliterator(), false)
                .collect(Collectors.toList()));

        StudentiEntity studentiEntity = this.studentService.getStudentById(stId);
        nameText.setText("Name: " + studentiEntity.getNume());
        groupText.setText("Group: "+ studentiEntity.getGrupa().toString());
        emailText.setText("Email: " + studentiEntity.getEmail());
    }

    @FXML
    public void initialize() {
        this.taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        this.gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("localDate"));

        this.tableView.setItems(model);

    }

    @FXML
    @SuppressWarnings("Duplicates")
    public void goBack() throws IOException {
        Parent root;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/confirmView.fxml"));
        root = loader3.load();
        ConfirmController mainController = loader3.getController();
        mainController.setSecondPane(pane);
        //mainController.setLoginService(studentService, temaService, notaService, profesorService, accountService);
        Scene newScene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setScene(newScene);
        stage2.show();
    }

}
