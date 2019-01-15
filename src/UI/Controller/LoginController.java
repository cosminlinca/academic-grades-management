package UI.Controller;

import Domain.ConturiEntity;
import Repository.*;
import Service.*;
import UI.MainGUI;
import Utils.PasswordSecurity;
import Validation.NotaValidator;
import Validation.ProfessorValidator;
import Validation.StudentValidator;
import Validation.TemaValidator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    StudentDBRepository studentDBRepository;
    StudentValidator studentValidator ;
    StudentService studentService;

    TemaDBRepository temaDBRepository;
    TemaValidator temaValidator;
    TemaService temaService;

    NotaDBRepository notaDBRepository;
    NotaValidator notaValidator;
    NotaService notaService;

    ProfesorDBRepository profesorDBRepository;
    ProfessorValidator professorValidator;
    ProfesorService profesorService;

    AccountDBRepository accountDBRepository;
    AccountService accountService;


    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> comboBoxRights;
    @FXML
    private Pane pane;
    @FXML
    private Pane ancPane;
    @FXML
    private Text comboT;
    private Stage stage2;

    public void setLoginService(StudentService studentService, TemaService temaService, NotaService notaService,
                                ProfesorService profesorService, AccountService accountService) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
        this.profesorService = profesorService;
        this.accountService = accountService;

        initModel();
    }

    private void initModel() {
    }

    @FXML
    public void initialize() {
        Parent root;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/waitView.fxml"));
        try {
            root = loader3.load();
            Scene newScene = new Scene(root);
            stage2 = new Stage();
            stage2.setScene(newScene);
            stage2.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        new SplashScreen().start();

        comboBoxRights.getItems().add("Student");
        comboBoxRights.getItems().add("Teacher");
        comboBoxRights.getItems().add("Admin");

        comboBoxRights.getSelectionModel().selectedItemProperty().addListener((x,y,z) -> handleComboBoxColor());
    }

    private void handleComboBoxColor() {
        if(comboBoxRights.getValue().equals("Student")) {
            comboT.setText("Student");
        }
        else if(comboBoxRights.getValue().equals("Teacher")) {
            comboT.setText("Teacher");
        }
        else {
            comboT.setText("Admin");
        }
    }

    class SplashScreen extends Thread {
        @Override
        public void run() {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                studentDBRepository = new StudentDBRepository();
                studentValidator = new StudentValidator();
                studentService = new StudentService(studentDBRepository, studentValidator);

                temaDBRepository = new TemaDBRepository();
                temaValidator = new TemaValidator();
                temaService = new TemaService(temaDBRepository, temaValidator);

                notaDBRepository = new NotaDBRepository();
                notaValidator = new NotaValidator();
                notaService = new NotaService(notaDBRepository, notaValidator, studentService, temaService);

                profesorDBRepository = new ProfesorDBRepository();
                professorValidator = new ProfessorValidator();
                profesorService = new ProfesorService(profesorDBRepository, professorValidator);

                accountDBRepository = new AccountDBRepository();
                accountService = new AccountService(accountDBRepository);

                Stage newStage = (Stage) ancPane.getScene().getWindow();
                newStage.show();

                stage2.close();
            });
        }
    }

    @FXML
    public void signInAction() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String right = comboBoxRights.getValue();
        if(right != null) {
            String decrypt = null;

            ConturiEntity conturiEntity = this.accountService.getByUsername(username);
            if (conturiEntity != null) {
                decrypt = PasswordSecurity.decrypt(conturiEntity.getPassword());
                if (conturiEntity.getUsername().equals(username) && decrypt.equals(password)) {
                    if (conturiEntity.getDrepturi().equals("s") && right.equals("Student")) {
                        Stage s = (Stage) pane.getScene().getWindow();
                        s.close();

                        Parent root;
                        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/studentGradesView.fxml"));
                        root = loader3.load();
                        StudentGradesController mainController = loader3.getController();
                        mainController.setStudentgService(studentService, temaService, notaService, conturiEntity.getIdUtilizator());
                        Scene newScene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(newScene);
                        stage.show();
                    } else if (conturiEntity.getDrepturi().equals("t") && right.equals("Teacher")) {
                        Stage s = (Stage) pane.getScene().getWindow();
                        s.close();

                        Parent root;
                        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/restrictiveMainMenu.fxml"));
                        root = loader3.load();
                        MainController mainController = loader3.getController();
                        mainController.setMainService(notaService, studentService, temaService, profesorService, accountService,"t", conturiEntity.getIdUtilizator());
                        mainController.setUser(conturiEntity.getIdUtilizator());

                        Scene newScene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(newScene);
                        stage.show();
                    } else if (conturiEntity.getDrepturi().equals("a") && right.equals("Admin")) {
                        Stage s = (Stage) pane.getScene().getWindow();
                        s.close();

                        Parent root;
                        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/mainView.fxml"));
                        root = loader3.load();
                        MainController mainController = loader3.getController();
                        mainController.setMainService(notaService, studentService, temaService, profesorService, accountService, "a", conturiEntity.getIdUtilizator());

                        Scene newScene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(newScene);
                        stage.show();
                    } else if (conturiEntity.getDrepturi().equals("s") && !right.equals("Student")) {
                        MessageAlert.showErrorMessage(null, "You don't have rights!");
                    } else if (conturiEntity.getDrepturi().equals("t") && !right.equals("Teacher")) {
                        MessageAlert.showErrorMessage(null, "You don't have rights!");
                    } else if (conturiEntity.getDrepturi().equals("a") && !right.equals("Admin")) {
                        MessageAlert.showErrorMessage(null, "You don't have rights!");
                    }
                } else {
                    MessageAlert.showErrorMessage(null, "Username/password incorrect!");
                }
            } else
                MessageAlert.showErrorMessage(null, "Username/password incorrect!");
        }
        else {
            MessageAlert.showErrorMessage(null, "Please select a right!");
        }

    }

    @FXML
    public void handleAddStAccount() throws IOException {
        Stage s = (Stage) pane.getScene().getWindow();
        s.close();

        Parent root;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/createStAccountView.fxml"));
        root = loader3.load();
        CreateStAccountController mainController = loader3.getController();
        mainController.setCreateStAccService(studentService, accountService);
        Scene newScene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    public void handleAddPAccount() throws IOException {
        Stage s = (Stage) pane.getScene().getWindow();
        s.close();

        Parent root;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/createPAccountView.fxml"));
        root = loader3.load();
        CreatePAccountController mainController = loader3.getController();
        mainController.setCreatePAccService(profesorService, accountService);
        Scene newScene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }
}
