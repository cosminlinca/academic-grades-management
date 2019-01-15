package UI;

import Repository.*;
import Service.*;
import UI.Controller.*;
import Validation.NotaValidator;
import Validation.StudentValidator;
import Validation.TemaValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class MainGUI extends Application {
    public static void main(String[] args) {
        launch(args); //creates an object of type Application
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root3;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/login.fxml"));
        root3 = loader3.load();
        LoginController mainController = loader3.getController();
        //mainController.setLoginService(studentService, temaService, notaService, profesorService, accountService)
        primaryStage.setScene(new Scene(root3));
        primaryStage.show();

    }
}
