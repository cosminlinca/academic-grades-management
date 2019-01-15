package UI.Controller;

import UI.MainGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmController {

    private Pane pane;
    @FXML
    private AnchorPane ancPane;

    public void setSecondPane(Pane pane) {
        this.pane = pane;
    }

    @FXML
    public void goBack() throws IOException {
        Stage currentStage = (Stage) ancPane.getScene().getWindow();
        currentStage.close();

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();

        Parent root;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/login.fxml"));
        root = loader3.load();
        LoginController mainController = loader3.getController();
        //mainController.setLoginService(studentService, temaService, notaService, profesorService, accountService);
        Scene newScene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setScene(newScene);
        //stage2.show();

    }

    @FXML
    public void closeW() {
        Stage currentStage = (Stage) ancPane.getScene().getWindow();
        currentStage.close();
    }
}
