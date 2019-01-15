package UI.Controller;

import Domain.*;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import Utils.Time;
import Validation.StudentPresentException;
import Validation.ValidationException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NotaSc2Controller {

    private Integer idUser;
    private NotaService notaService;
    private StudentService studentService;
    private TemaService temaService;
    private ObservableList<NotaDTO> model = FXCollections.observableArrayList();

    public void setNota2Service(Integer idUser,NotaService notaService, StudentService studentService, TemaService temaService) {
        this.idUser = idUser;
        this.notaService = notaService;
        this.studentService = studentService;
        this.temaService = temaService;
        initModel();
    }
    private String group;
    private String task;

    //dynamic table
    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private ComboBox<String> groupComboBox;
    @FXML
    private ComboBox<String> tasksComboBox;
    @FXML
    private TextField gradeTextField;
    @FXML
    private DatePicker date;


    private void initModel() {
        //add columns
        Iterator<TemeEntity> temaIterator = this.temaService.getTemaIterator();
        int index = 0, i = 0;
        while (temaIterator.hasNext())
        {
            TemeEntity itAux = temaIterator.next();
            tasksComboBox.getItems().add(itAux.getDescriere());
            if (itAux.getDeadline() == (Time.getCurrentUniversityWeek())) {
                index = i;
                task = itAux.getDescriere();
            }
            i++;
        }
        tasksComboBox.getSelectionModel().select(index);
        task = tasksComboBox.getValue();

        this.groupComboBox.getSelectionModel().selectedItemProperty().addListener((x,y,z)->handleGroupSelection());
        this.tasksComboBox.getSelectionModel().selectedItemProperty().addListener((x,y,z)->handleTaskSelection());
        date.setValue(LocalDate.now());
    }

    @SuppressWarnings("Duplicates")
    @FXML
    private void addHandler() {
        LocalDate localDate = date.getValue();
        Integer temaId = this.temaService.findByName(task).getID();
        Iterator<StudentiEntity> studentIterator = this.studentService.getStudentsIterator();
        while(studentIterator.hasNext()) {
            StudentiEntity auxStudent = studentIterator.next();
            if (auxStudent.getGrupa().equals(Integer.parseInt(group)) && auxStudent.isPrezent()) {
                //e din grupa x
                Iterator<NoteEntity> notaIterator = this.notaService.getGradesIterator();
                int ok = 1;
                while (notaIterator.hasNext()) {
                    NoteEntity auxNota = notaIterator.next();
                    if (auxNota.getIdStudent().equals(auxStudent.getID()) && auxNota.getIdTema().equals(temaId)) {
                        //studentul y din grupa x are o nota la o tema
                        ok = 0;
                    }
                }
                if (ok == 1)
                {
                    Double nota = Double.parseDouble(gradeTextField.getText());
                    try{
                        if(auxStudent.getNume().startsWith("A") || auxStudent.getNume().startsWith("B") || auxStudent.getNume().startsWith("C"))
                            nota = nota - 2;
                        if(auxStudent.getNume().startsWith("D") || auxStudent.getNume().startsWith("E") || auxStudent.getNume().startsWith("F"))
                            nota = nota + 1;
                        if(auxStudent.getNume().startsWith("G") || auxStudent.getNume().startsWith("M") || auxStudent.getNume().startsWith("N"))
                            nota = nota - 1;
                        if(auxStudent.getNume().startsWith("P") || auxStudent.getNume().startsWith("S") || auxStudent.getNume().startsWith("T"))
                            nota = nota - 3;

                        this.notaService.addNota(auxStudent.getID(), temaId, idUser, nota, localDate, "bravo", true);
                    }catch (StudentPresentException e) {
                        MessageAlert.showErrorMessage(null, "Unii studenti sunt absenti!");
                    }catch (ValidationException e) {
                        MessageAlert.showErrorMessage(null, e.getError());
                    }

                }

            }
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Done!" , "Grades added");
        handleGroupSelection();
    }

    @SuppressWarnings("Duplicates")
    private void handleGroupSelection() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        group = this.groupComboBox.getValue();
        List<String> columnNames = Arrays.asList(new String[]{"Group " + group, task});
        for(int i = 0; i<columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
            tableView.getColumns().add(column);
        }
        Integer temaId = this.temaService.findByName(task).getID();
        //add data
        List<String> data = Arrays.asList(new String[]{"",""});
        Iterator<StudentiEntity> studentIterator = this.studentService.getStudentsIterator();
        while(studentIterator.hasNext()) {
            StudentiEntity auxStudent = studentIterator.next();
            if (auxStudent.getGrupa().equals(Integer.parseInt(group))) {
                //e din grupa x
                Iterator<NoteEntity> notaIterator = this.notaService.getGradesIterator();
                data.set(0, auxStudent.getNume());
                while (notaIterator.hasNext()) {
                    NoteEntity auxNota = notaIterator.next();
                    if (auxNota.getIdStudent().equals(auxStudent.getID()) && auxNota.getIdTema().equals(temaId)) {
                        //studentul y din grupa x are o nota la o tema
                        data.set(1, auxNota.getValoare().toString());
                    }
                }
                tableView.getItems().add(FXCollections.observableArrayList(data));
                for (int i = 0; i < 2; i++)
                    data.set(i, "");
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void handleTaskSelection() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        task = this.tasksComboBox.getValue();
        List<String> columnNames = Arrays.asList(new String[]{"Group " + group, task});
        for(int i = 0; i<columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
            tableView.getColumns().add(column);
        }

        Integer temaId = this.temaService.findByName(task).getID();
        //add data
        List<String> data = Arrays.asList("","");
        Iterator<StudentiEntity> studentIterator = this.studentService.getStudentsIterator();
        while(studentIterator.hasNext()) {
            StudentiEntity auxStudent = studentIterator.next();
            if(auxStudent.getGrupa().equals(Integer.parseInt(group))) {
                //e din grupa x
                Iterator<NoteEntity> notaIterator = this.notaService.getGradesIterator();
                data.set(0,auxStudent.getNume());
                while(notaIterator.hasNext()) {
                    NoteEntity auxNota = notaIterator.next();
                    if(auxNota.getIdStudent().equals(auxStudent.getID()) && auxNota.getIdTema().equals(temaId)) {
                        //studentul y din grupa x are o nota la o tema
                        data.set(1, auxNota.getValoare().toString());
                    }
                }
                //if(data.get(0) != "")
                tableView.getItems().add(FXCollections.observableArrayList(data));
                for(int i=0; i<2; i++)
                    data.set(i,"");

            }
        }
    }
    @FXML
    private void initialize() {
        String[] groups = {"221","222","223","224","225","226","227"};
        groupComboBox.getItems().addAll(groups);

        groupComboBox.setValue("221");
        group = "221";
    }
}
