package UI.Controller;

import Domain.ProfesoriEntity;
import Service.*;
import UI.MainGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    private ProfesorService profesorService;
    private AccountService accountService;
    private String right;
    private Integer idUser;

    public void setMainService(NotaService notaService, StudentService studentService, TemaService temaService, ProfesorService profesorService, AccountService accountService, String right, Integer id) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
        this.profesorService = profesorService;
        this.accountService = accountService;
        this.right = right;
        this.idUser = id;
        initModel();
    }

    private void initModel() {
        if(right.equals("t")) {
            ProfesoriEntity profesoriEntity = this.profesorService.getById(idUser);
            this.userName.setText(profesoriEntity.getNume());
        }

    }

    public void setUser(Integer id) {
        this.idUser = id;
    }

    @FXML
    private Pane studentsButton;
    @FXML
    private Pane tasksButton;
    @FXML
    private Pane gradesButton;
    @FXML
    private Pane pane;
    @FXML
    private Text userName;

    @FXML
    public void handleStudentsLayer() throws IOException {
        Stage stage = (Stage) studentsButton.getScene().getWindow();
        stage.hide();

        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/studentView.fxml"));
        root = loader.load();
        StController controller = loader.getController();
        controller.setStudentService(this.studentService, this.temaService, this.notaService, this.accountService, stage);
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();

    }
    @FXML
    public void handleTasksLayer() throws IOException {
        Stage stage = (Stage) tasksButton.getScene().getWindow();
        stage.close();

        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/temaView.fxml"));
        root = loader.load();
        TemaController controller = loader.getController();
        controller.setTemaService(this.temaService, stage);
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();

    }
    @FXML
    public void handleGradesLayer() throws IOException {
        Stage stage = (Stage) gradesButton.getScene().getWindow();
        stage.close();

        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/notaView.fxml"));
        root = loader.load();
        NotaController controller = loader.getController();
        controller.setNotaService(this.notaService, this.studentService, this.temaService, idUser, stage);
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }
    @FXML
    public void handleReportsLayer() throws IOException {
        Stage stage = (Stage) gradesButton.getScene().getWindow();
        stage.close();

        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/rapoarteView.fxml"));
        root = loader.load();
        RapoarteController controller = loader.getController();
        controller.setRapoarteService(this.studentService, this.temaService, this.notaService, stage);
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

    @FXML
    public void handleProfessorLayer() throws IOException {
        Stage stage = (Stage) gradesButton.getScene().getWindow();
        stage.close();

        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/professorView.fxml"));
        root = loader.load();
        ProfessorController controller = loader.getController();
        controller.setProfessorService(this.profesorService, stage);
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
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
