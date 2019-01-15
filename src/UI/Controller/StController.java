package UI.Controller;

import Domain.ConturiEntity;
import Domain.StudentiEntity;
import Service.AccountService;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import Utils.Observer;
import Utils.StudentChangeEvent;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StController implements Observer<StudentChangeEvent> {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    private AccountService accountService;
    private String right;

    private Stage parentStage;

    private ObservableList<StudentiEntity> model = FXCollections.observableArrayList();


    @FXML
    private Button deleteButton;
    @FXML
    private Button markAsMissingButton;
    @FXML
    private Button markAsPresent;
    @FXML
    private TableView<StudentiEntity> tableView;
    @FXML
    private TableColumn<StudentiEntity, Integer> idColumn;
    @FXML
    private TableColumn<StudentiEntity, String> nameColumn;
    @FXML
    private TableColumn<StudentiEntity, Integer> groupColumn;
    @FXML
    private TableColumn<StudentiEntity, String> emailColumn;
    @FXML
    private TableColumn<StudentiEntity, Boolean> presentColumn;
    @FXML
    private Pane back;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldGroup;
    @FXML
    private Pagination pagination;
    private final static int rowPerPage = 12;

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        Iterable<StudentiEntity> iterable = () -> this.studentService.getStudentsIterator();
        model.setAll(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()));

        pagination.setPageFactory(this::createPage);
    }

    public void setStudentService(StudentService studentService, TemaService temaService, NotaService notaService, AccountService accountService,Stage stage) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
        this.accountService = accountService;
        this.parentStage = stage;
//        this.accountService = accountService;
//        this.right = right;
        studentService.addObserver(this);
        initModel();

    }

    private void initModel() {
        Iterable<StudentiEntity> iterable = () -> this.studentService.getStudentsIterator();
        model.setAll(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()));

    }

    @FXML
    public void initialize() {
        listen();
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        this.groupColumn.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.presentColumn.setCellValueFactory(new PropertyValueFactory<>("prezent"));

        //this.tableView.setItems(model);
        pagination.setPageFactory(this::createPage);

        this.tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    showStudentDetails(newValue);
                    });
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
    public void showStudentDetails(StudentiEntity value) {
        if (value==null)
        {
            textFieldName.setText("");
            textFieldGroup.setText("");
            textFieldEmail.setText("");

        }
        else
        {
            textFieldName.setText(value.getNume());
            textFieldGroup.setText(value.getGrupa().toString());
            textFieldEmail.setText(value.getEmail());
        }
    }


    @FXML
    public void handleAddStudent() {
        String name = textFieldName.getText();
        String email = textFieldEmail.getText();
        String g = textFieldGroup.getText();
        try{
            if(g.equals("")) {
                StudentiEntity saved = this.studentService.addStudent(name, email);
                if(saved == null) {
                    MessageAlert.showMessage(null,Alert.AlertType.INFORMATION, "Saved", "Student saved");
                    showStudentDetails(null);
                }
                else {
                    MessageAlert.showErrorMessage(null, "Student already exists!");
                }
            }
            else{
                try{
                    Integer group = Integer.parseInt(g);
                    StudentiEntity saved = this.studentService.addStudent(name, group, email);
                    if(saved == null) {
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Saved", "Student saved");
                        showStudentDetails(null);
                    }
                    else  MessageAlert.showErrorMessage(null, "Student already exists!");
                }catch(NumberFormatException e) {
                    MessageAlert.showErrorMessage(null, "The group can only contain digits");
                }

            }
        }catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getError());
        }
    }


    public void handleUpdateStudent() {
        String name = textFieldName.getText();
        String email = textFieldEmail.getText();
        try {

            Integer group = Integer.parseInt(textFieldGroup.getText());
            Integer id = tableView.getSelectionModel().getSelectedItems().get(0).getID();
            StudentiEntity updated = this.studentService.updateStudent(id, name,group,email);
            if(updated != null) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Updated", "Student updated");
                showStudentDetails(null);
            }
            else MessageAlert.showErrorMessage(null, "Student already exists!");

        }catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getError());
        }
        catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Invalid group");
        }
        catch (NullPointerException e) {
            MessageAlert.showErrorMessage(null, "Please select a student from table!");
        }
    }

    public void handleDeleteStudent() {
        StudentiEntity student = tableView.getSelectionModel().getSelectedItem();
        Integer id;
        if(student != null)
        {
            id = student.getID();
            try{
                StudentiEntity deleted = this.studentService.deleteStudent(id);
                this.notaService.deleteGradesForAStudent(deleted.getID());
                if(deleted != null) {
                    ConturiEntity conturiEntity = this.accountService.deleteAccount(deleted.getID(), "s");
                    if(conturiEntity != null) {
                        MessageAlert.showMessage(null,Alert.AlertType.INFORMATION, "Deleted!" , "Student deleted!" + "Account deleted!");
                        showStudentDetails(null);
                    }
                    else {
                        MessageAlert.showMessage(null,Alert.AlertType.INFORMATION, "Deleted!" , "Student deleted!");
                        showStudentDetails(null);
                    }
                }
                else{
                    MessageAlert.showErrorMessage(null, "Can't delelet");
                }

            }catch(ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getError());
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Please select a student from table!");
        }

    }

    public void handleTempDelStudent() {
        StudentiEntity student = tableView.getSelectionModel().getSelectedItem();
        if(student != null) {
            Integer id = student.getID();
            StudentiEntity marked = this.studentService.markStudentAsMissing(id);
            if(marked != null) {
                MessageAlert.showMessage(null,Alert.AlertType.INFORMATION, "Marked!" , "Student is now absent!");
                showStudentDetails(null);
            }
            else {
                MessageAlert.showErrorMessage(null, "Can't mark this student!");
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Please select a student from table!");
        }

    }

    public void handlePresent() {
        StudentiEntity student = tableView.getSelectionModel().getSelectedItem();
        if(student != null) {
            Integer id = student.getID();
            StudentiEntity marked = this.studentService.markStudentAsPresent(id);
            if(marked != null) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Marked!" , "Student is now present!");
                showStudentDetails(null);
            }
            else {
                MessageAlert.showErrorMessage(null,"Can't mark this student!");
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Please select a student from table!");
        }

    }


    public void handleClear() {
        showStudentDetails(null);
    }

    @FXML
    @SuppressWarnings("Duplicates")
    public void handleGoBack(){
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();

        parentStage.show();
    }

        /**
     * add listener for buttons
     */
    private void listen() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                StudentiEntity student = tableView.getSelectionModel().getSelectedItem();
                if(student.isPrezent() == true) {
                    deleteButton.setDisable(false);
                    markAsMissingButton.setDisable(false);
                    markAsPresent.setDisable(true);
                }
                else {
                    deleteButton.setDisable(false);
                    markAsMissingButton.setDisable(true);
                    markAsPresent.setDisable(false);
                }
            }
            else {
                deleteButton.setDisable(true);
                markAsMissingButton.setDisable(true);
                markAsPresent.setDisable(true);
            }
        });
    }


}
