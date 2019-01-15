//package UI.Controller;
//
//import Domain.Student;
//import Domain.StudentiEntity;
//import Service.NotaService;
//import Service.StudentService;
//import Service.TemaService;
//import UI.MainGUI;
//import UI.View.StudentView;
//import Utils.Observer;
//import Utils.StudentChangeEvent;
//import Validation.ValidationException;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//public class StudentController implements Observer<StudentChangeEvent> {
//    private StudentService studentService;
//    private TemaService temaService;
//    private NotaService notaService;
//    private String right;
//
//    private ObservableList<StudentiEntity> model;
//    private StudentView view;
//
//    public StudentController(StudentService studentService, TemaService temaService, NotaService notaService, String right) {
//        this.studentService = studentService;
//        this.temaService  = temaService;
//        this.notaService = notaService;
//        this.right = right;
//        //<Student> iterable = () -> studentService.getStudentsIterator();
//        List<StudentiEntity> list = StreamSupport.stream(studentService.getAll().spliterator(), false)
//                .collect(Collectors.toList());
//        model = FXCollections.observableArrayList(list);
//        this.studentService.addObserver(this);
//    }
//
//    public void setTemaService(TemaService temaService) {
//        this.temaService = temaService;
//    }
//
//    public void setNotaService(NotaService notaService) {
//        this.notaService = notaService;
//    }
//
//    @Override
//    public void update(StudentChangeEvent studentChangeEvent) {
//        //Iterable<Student> iterable = () -> this.studentService.getStudentsIterator();
//        model.setAll(StreamSupport.stream(studentService.getAll().spliterator(), false)
//                .collect(Collectors.toList()));
//    }
//
//    public ObservableList<StudentiEntity> getModel() {
//        return model;
//    }
//
//    public StudentView getView() {
//        return view;
//    }
//
//    public void setView(StudentView view) {
//        this.view = view;
//    }
//
//    public void showMessageTaskDetails(StudentiEntity value) {
//        if (value==null)
//        {
//            view.textFieldId.setText("");
//            view.textFieldName.setText("");
//            view.textFieldGroup.setText("");
//            view.textFieldEmail.setText("");
//
//        }
//        else
//        {
//            view.textFieldId.setText(value.getID().toString());
//            view.textFieldName.setText(value.getNume());
//            view.textFieldGroup.setText(value.getGrupa().toString());
//            view.textFieldEmail.setText(value.getEmail());
//        }
//    }
//
//    public void handleAddStudent(ActionEvent actionEvent) {
//        String name = this.view.textFieldName.getText();
//        String email = this.view.textFieldEmail.getText();
//        String g = this.view.textFieldGroup.getText();
//        try{
//            Integer id = Integer.parseInt(this.view.textFieldId.getText());
//            if(g.equals("")) {
//                StudentiEntity saved = this.studentService.addStudent(id, name, email);
//                if(saved == null) {
//                    showMessage(Alert.AlertType.INFORMATION, "Saved!" , "Student added!");
//                    showMessageTaskDetails(null);
//                }
//                else {
//                    showErrorMessage("Student already exists!");
//                }
//            }
//            else{
//                Integer group = Integer.parseInt(g);
//                StudentiEntity saved = this.studentService.addStudent(id, name, group, email);
//                if(saved == null) {
//                    showMessage(Alert.AlertType.INFORMATION, "Saved!" , "Student added!");
//                    showMessageTaskDetails(null);
//                }
//                else showErrorMessage("Student already exists!");
//            }
//        }catch (ValidationException e) {
//            showErrorMessage(e.getError());
//        }
//        catch (NumberFormatException e) {
//            showErrorMessage("ID invalid");
//        }
//
//    }
//
//    static void showMessage(Alert.AlertType type, String header, String text){
//        Alert message=new Alert(type);
//        message.setHeaderText(header);
//        message.setContentText(text);
//        message.showAndWait();
//    }
//
//    static void showErrorMessage(String text){
//        Alert message=new Alert(Alert.AlertType.ERROR);
//        message.setTitle("ERROR");
//        message.setContentText(text);
//        message.showAndWait();
//    }
//
//    public void handleUpdateStudent(ActionEvent actionEvent) {
//
//        String name = this.view.textFieldName.getText();
//        String email = this.view.textFieldEmail.getText();
//        try {
//            Integer id = Integer.parseInt(this.view.textFieldId.getText());
//            Integer group = Integer.parseInt(this.view.textFieldGroup.getText());
//            StudentiEntity updated = this.studentService.updateStudent(id,name,group,email);
//            if(updated != null) {
//                showMessage(Alert.AlertType.INFORMATION, "Updated!" , "Student updated!");
//                showMessageTaskDetails(null);
//            }
//            else showErrorMessage("Student already exists!");
//
//        }catch (ValidationException e) {
//            showErrorMessage(e.getError());
//        }
//        catch (NumberFormatException e) {
//            showErrorMessage("ID invalid");
//        }
//
//    }
//
//    public void handleDeleteStudent(ActionEvent actionEvent) {
//        StudentiEntity student = view.tableView.getSelectionModel().getSelectedItem();
//        Integer id = student.getID();
//        try{
//            StudentiEntity deleted = this.studentService.deleteStudent(id);
//            if(deleted != null) {
//                showMessage(Alert.AlertType.INFORMATION, "Deleted!" , "Student deleted!");
//                showMessageTaskDetails(null);
//            }
//            else{
//                showErrorMessage("Can't delete!");
//            }
//
//        }catch(ValidationException e) {
//            showErrorMessage(e.getError());;
//        }
//
//
//    }
//
//    public void handleTempDelStudent(ActionEvent actionEvent) {
//        StudentiEntity student = view.tableView.getSelectionModel().getSelectedItem();
//        Integer id = student.getID();
//        StudentiEntity marked = this.studentService.markStudentAsMissing(id);
//        if(marked != null) {
//            showMessage(Alert.AlertType.INFORMATION, "Marked!" , "Student is now absent!");
//            showMessageTaskDetails(null);
//        }
//        else {
//            showErrorMessage("Can't mark this student!");
//        }
//    }
//
//    public void handlePresent(ActionEvent actionEvent) {
//        StudentiEntity student = view.tableView.getSelectionModel().getSelectedItem();
//        Integer id = student.getID();
//        StudentiEntity marked = this.studentService.markStudentAsPresent(id);
//        if(marked != null) {
//            showMessage(Alert.AlertType.INFORMATION, "Marked!" , "Student is now present!");
//            showMessageTaskDetails(null);
//        }
//        else {
//            showErrorMessage("Can't mark this student!");
//        }
//    }
//
//    public void handleGoBack(ActionEvent actionEvent){
//        Stage stage = (Stage) view.back.getScene().getWindow();
//        stage.close();
//
//        Parent root;
//        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/mainView.fxml"));
//        try {
//            root = loader.load();
//            MainController controller = loader.getController();
//            controller.setMainService(notaService, studentService, temaService, right);
//            Scene newScene = new Scene(root);
//            Stage newStage = new Stage();
//            newStage.setScene(newScene);
//            newStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
