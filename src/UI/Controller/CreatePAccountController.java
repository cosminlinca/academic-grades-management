package UI.Controller;

import Domain.ConturiEntity;
import Domain.ProfesoriEntity;
import Service.AccountService;
import Service.ProfesorService;
import UI.MainGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class CreatePAccountController {
    private ProfesorService profesorService;
    private AccountService accountService;

    public void setCreatePAccService(ProfesorService profesorService, AccountService accountService) {
        this.profesorService = profesorService;
        this.accountService = accountService;
    }

    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField password2Field;
    @FXML
    private Pane pane;

    @FXML
    private void handleCreateAccount() throws IOException {
        String name = nameField.getText();
        ProfesoriEntity profesoriEntity = this.profesorService.findByName(name);
        if(profesoriEntity == null) {
            MessageAlert.showErrorMessage(null, "This professor does not exist");
        }
        else {
            //create account
            String username = this.usernameField.getText();
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
                            this.accountService.addAccount(username, password1, "t", profesoriEntity.getID());
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

    @SuppressWarnings("Duplicates")
    @FXML
    private void handleGoBack() {
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
