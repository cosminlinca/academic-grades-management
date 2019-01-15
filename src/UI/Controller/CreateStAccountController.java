package UI.Controller;

import Domain.ConturiEntity;
import Domain.StudentiEntity;
import Service.AccountService;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import UI.MainGUI;
import Utils.PasswordSecurity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class CreateStAccountController {
    private StudentService studentService;
    private AccountService accountService;


    @FXML
    private TextField nameField;
    @FXML
    private TextField groupField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField password2Field;
    @FXML
    private Pane pane;

    public void setCreateStAccService(StudentService studentService, AccountService accountService) {
        this.studentService = studentService;
        this.accountService = accountService;

        initModel();
    }

    private void initModel() {
    }


    @FXML
    private void handleCreateAccount() {
        String name = nameField.getText();
        String group = groupField.getText();
        try
        {
            Integer g = Integer.parseInt(group);
            ConturiEntity conturiEntity = null;
            StudentiEntity studentiEntity = this.studentService.findByName(name, g);
            if(studentiEntity != null)
            {
                conturiEntity = this.accountService.findById(studentiEntity.getID());
                if(conturiEntity != null) {
                    MessageAlert.showErrorMessage(null, "This student already has an account");
                }
                else {
                    //create account
                    String username = usernameField.getText();
                    if(username.equals("")) {
                        MessageAlert.showErrorMessage(null, "Invalid username");
                    }
                    else {
                        ConturiEntity cE = this.accountService.getByUsername(username);
                        if(cE != null) {
                            MessageAlert.showErrorMessage(null, "This username already exist");
                        }
                        else {
                            String password1 = passwordField.getText();
                            String password2 = password2Field.getText();
                            if(password1.equals("")) {
                                MessageAlert.showErrorMessage(null, "Invalid password");
                            }
                            else {
                                if(!password1.equals(password2)) {
                                    MessageAlert.showErrorMessage(null, "Passwords do not match");
                                }
                                else {
                                    this.accountService.addAccount(username, password1, "s", studentiEntity.getID());
                                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Done!", "Account created");
                                    Stage s = (Stage) pane.getScene().getWindow();
                                    s.close();

                                    Parent root;
                                    FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/login.fxml"));
                                    root = loader3.load();
                                    LoginController mainController = loader3.getController();
                                    //mainController.setLoginService(studentService, temaService, notaService, profesorService, accountService);
                                    Scene newScene = new Scene(root);
                                    Stage stage2 = new Stage();
                                    stage2.setScene(newScene);

                                }
                            }
                        }
                    }
                }
            }
            else {
                MessageAlert.showErrorMessage(null, "This student does not exist");
            }

        }catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Invalid group");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    @SuppressWarnings("Duplicates")
    private void handleGoBack(){
        Parent root;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/confirmView.fxml"));
        try {
            root = loader3.load();
            ConfirmController mainController = loader3.getController();
            mainController.setSecondPane(pane);
            //mainController.setLoginService(studentService, temaService, notaService, profesorService, accountService);
            Scene newScene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setScene(newScene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
