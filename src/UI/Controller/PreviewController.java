package UI.Controller;

import Domain.*;
import Service.NotaService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sun.plugin2.message.Message;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.time.LocalDate;

public class PreviewController {
    private Integer idUser;
    private NotaService notaService;
    private String feedback;
    private StudentiEntity student;
    private TemeEntity tema;
    private ProfesoriEntity profesor;
    private LocalDate localDate;

    @FXML
    private TextField nameField;
    @FXML
    private TextField groupField;
    @FXML
    private TextField idField;
    @FXML
    private TextField taskNameField;
    @FXML
    private CheckBox motivatedCheckBox;
    @FXML
    private TextField dateField;
    @FXML
    private TextField gradeField;
    @FXML
    private Button cancelButton;

    public void setData(Integer idUser,StudentiEntity student, TemeEntity tema, LocalDate localDate, Double nota, Boolean motivated, String feedback, NotaService notaService) {
        this.idUser = idUser;
        this.idField.setText(student.getID().toString());
        this.nameField.setText(student.getNume());
        this.groupField.setText(student.getGrupa().toString());
        this.motivatedCheckBox.setSelected(motivated);
        this.taskNameField.setText(tema.getDescriere());
        this.gradeField.setText(nota.toString());
        this.dateField.setText(localDate.toString());


        this.notaService = notaService;
        this.student = student;
        this.tema = tema;
        this.feedback = feedback;
        this.localDate = localDate;
        setFieldState();
    }
    @FXML
    private void initialize() {
        this.idField.setText("");
        this.nameField.setText("");
        this.groupField.setText("");
        this.taskNameField.setText("");
        this.gradeField.setText("");
        this.dateField.setText("");

    }

    private void setFieldState() {
        this.idField.setEditable(false);
        this.nameField.setEditable(false);
        this.groupField.setEditable(false);
        this.taskNameField.setEditable(false);
        this.gradeField.setEditable(false);
        this.dateField.setEditable(false);
        /*
        this.motivatedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(this.motivatedCheckBox.isSelected())
                    this.motivatedCheckBox.setSelected(false);
                else
                    this.motivatedCheckBox.setSelected(true);
            }catch (StackOverflowError e) {

            }
        });
        */
    }
    @FXML
    public void handleAddConfirm() {
        NoteEntity nota = this.notaService.addNota(student.getID(), tema.getID(), idUser, Double.parseDouble(this.gradeField.getText()), this.localDate, this.feedback, this.motivatedCheckBox.isSelected());
        if(nota != null)
            MessageAlert.showErrorMessage(null, "Grade already exists!");
        else
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Added", "Grade added!");


    }
    @FXML
    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
