package UI.Controller;

import Service.AccountService;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import UI.MainGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class RapoarteController {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    private Stage parentStage;

    @FXML
    private Pane back;

    public void setRapoarteService(StudentService studentService, TemaService temaService, NotaService notaService, Stage stage) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
        this.parentStage = stage;
    }

    @FXML
    public void mediaTemelorAction() {

        Parent root;
        FXMLLoader loader3 = new FXMLLoader(MainGUI.class.getResource("../UI/View/mediiPerTeme.fxml"));
        try {
            root = loader3.load();
            MediiPerTemeController mainController = loader3.getController();
            mainController.setMediiTemeService(studentService, temaService, notaService);
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void intraInExamenAction() {
        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/stExamenView.fxml"));
        try {
            root = loader.load();
            StExamenController controller = loader.getController();
            controller.setMediiService(this.studentService,this.temaService, this.notaService);
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void noteLabAction() {
        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/mediiView.fxml"));
        try {
            root = loader.load();
            MediiController controller = loader.getController();
            controller.setMediiService(this.studentService,this.temaService, this.notaService);
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    @SuppressWarnings("Duplicates")
    public void handleGoBack() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();

        parentStage.show();
    }
}
