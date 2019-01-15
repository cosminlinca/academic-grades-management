package UI.Controller;

import Domain.*;
import Service.AccountService;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import UI.MainGUI;
import Utils.NotaChangeEvent;
import Utils.Observer;
import Utils.TemaChangeEvent;
import Utils.Time;
import Validation.StudentPresentException;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NotaController implements Observer<NotaChangeEvent> {
    private NotaService notaService;
    private StudentService studentService;
    private TemaService temaService;
    private Integer idUser;
    private Stage parentStage;

    private ObservableList<NotaDTO> model = FXCollections.observableArrayList();

    public void setNotaService(NotaService notaService, StudentService studentService, TemaService temaService, Integer id, Stage stage) {
        this.notaService = notaService;
        this.studentService = studentService;
        this.temaService = temaService;
        this.idUser = id;
        this.parentStage = stage;
        this.notaService.addObserver(this);
        initModel();
    }

    private void initModel() {
        //set Table View
        List<NotaDTO> notaDTOS = getNotaDTOList();
        model.setAll(StreamSupport.stream(notaDTOS.spliterator(), false)
                .collect(Collectors.toList()));

        TreeSet<Integer> groups = new TreeSet<>();
        Iterator<StudentiEntity> studentIterator = this.studentService.getStudentsIterator();
        while(studentIterator.hasNext()) {
            StudentiEntity studentiEntity = studentIterator.next();
            comboName.getItems().add(studentiEntity.getNume());
            groups.add(studentiEntity.getGrupa());
        }
        for (Integer g: groups
             ) {
            comboGroup.getItems().add(g.toString());
        }
        //autoComplete class
        new AutoCompleteComboBoxListener<>(comboName);
        new AutoCompleteComboBoxListener<>(comboGroup);

        //set ComboBox for Tasks
        Iterator<TemeEntity> temaIterator = this.temaService.getTemaIterator();
        int index = 0, i = 0;
        while (temaIterator.hasNext()) {
            TemeEntity itAux = temaIterator.next();
            comboBoxTasks.getItems().add(itAux.getDescriere() + "/D:" + itAux.getDeadline());
            if (itAux.getDeadline() == (Time.getCurrentUniversityWeek())) {
                index = i;
            }
            i++;
        }
        comboBoxTasks.getSelectionModel().select(index);

        //set DatePicker default value - current date
        dateField.setValue(LocalDate.parse(LocalDate.now().format(Nota.dateTimeFormatter())));
//        String v = String.valueOf(dateField.getValue());
//        System.out.println(v);

        //set ComboBox for Feedbacks
        String[] feedbacks = {"Excelent!", "Foarte bine, tine-o tot asa!" , "Mai lucreaza la validarea datelor, in rest totul e bine!"};
        comboBoxFeedbacks.getItems().addAll(Arrays.asList(feedbacks));

        this.comboBoxTasks.getSelectionModel().selectedItemProperty().addListener((x,y,z)->handleFeedbackGrade());
    }

    @FXML
    private TableView<NotaDTO> tableView;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnName;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnTask;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnGrade;
    @FXML
    private TableColumn<NotaDTO, String> tableColumnDate;
    @FXML
    private TableColumn<NotaDTO, Integer> tableColumnGroup;


    @FXML
    private ComboBox<String> comboName;
    @FXML
    private ComboBox<String> comboGroup;
    @FXML
    private ComboBox<String> comboBoxTasks;
    @FXML
    private TextField gradeField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<String> comboBoxFeedbacks;
    @FXML
    private TextArea feedbackArea;
    @FXML
    private CheckBox motivatedCheckBox;
    @FXML
    private Pane back;
    @FXML
    private TextField filterStudentName;
    @FXML
    private TextField filterTaskName;
    @FXML
    private DatePicker filterStartDate;
    @FXML
    private DatePicker filterFinalDate;
    @FXML
    private ComboBox<String> filterGroupComboBox;
    @FXML
    private Pagination pagination;
    private final static int rowPerPage = 8;



    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnTask.setCellValueFactory(new PropertyValueFactory<>("task"));
        tableColumnGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnGroup.setCellValueFactory(new PropertyValueFactory<>("group"));

        //this.tableView.setItems(model);


        this.filterStartDate.setValue(LocalDate.parse("2018-06-01"));
        this.filterFinalDate.setValue(LocalDate.parse("2019-03-30"));
        String[] groups = {"221","222","223","224","225","226","227"};
        filterGroupComboBox.getItems().addAll(groups);

        this.comboBoxFeedbacks.getSelectionModel().selectedItemProperty().addListener((x,y,z)->handleFeedback());
        filterStudentName.textProperty().addListener(o -> handleFilter());
        filterTaskName.textProperty().addListener(o -> handleFilter());
        filterStartDate.valueProperty().addListener(o->handleFilter());
        filterFinalDate.valueProperty().addListener(o->handleFilter());

        pagination.setPageFactory(this::createPage);

    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowPerPage;
        int toIndex = Math.min(fromIndex + rowPerPage, model.size());
        if(fromIndex <= toIndex)
            tableView.setItems(FXCollections.observableArrayList(model.subList(fromIndex, toIndex)));
        else tableView.setItems(null);
        return tableView;
    }

    private void handleFeedbackGrade() {
        this.feedbackArea.setText("");
        if(!motivatedCheckBox.isSelected()) {
            String task = this.comboBoxTasks.getValue();
            String task_name = task.split("/")[0];
            TemeEntity tema = this.temaService.findByName(task_name);
            LocalDate localDate = dateField.getValue();
            int diff = (Time.getUniversityWeek(localDate)) - tema.getDeadline();
            if(diff>0 && diff<=2) {
                feedbackArea.setText("Nota a fost diminuata cu " + 2.5*diff + " puncte datorita intarzierii");
            }
            else if(diff > 2) {
                feedbackArea.setText("Ati obtinut nota 1 pe aceasta tema... :(");
            }
            else
            {
                feedbackArea.setText("");
            }

        }
    }

    private void handleFeedback() {
        String textFeedback = this.comboBoxFeedbacks.getValue();
        this.feedbackArea.setText(textFeedback);
    }

    @Override
    public void update(NotaChangeEvent notaChangeEvent) {
        List<NotaDTO> notaDTOS = getNotaDTOList();
        model.setAll(StreamSupport.stream(notaDTOS.spliterator(), false)
                .collect(Collectors.toList()));

        pagination.setPageFactory(this::createPage);
    }

    private List<NotaDTO> getNotaDTOList() {
        Iterator<NoteEntity> iterator = this.notaService.getGradesIterator();
        List<NotaDTO> notaDTOS = new ArrayList<>();
        while (iterator.hasNext()) {
            NoteEntity itAux = iterator.next();
            try {
                notaDTOS.add(new NotaDTO(
                                this.studentService.getStudentById(itAux.getIdStudent()).getNume(),
                                this.studentService.getStudentById(itAux.getIdStudent()).getGrupa(),
                                this.temaService.getTemaById(itAux.getIdTema()).getDescriere(),
                                itAux.getValoare(),
                                itAux.getData()
                        )
                );
            }catch (NullPointerException e) {

            }

        }
        return notaDTOS;

    }
    @FXML
    public void handleNota() {
        try {
            String name = comboName.getValue();
            Integer group = Integer.parseInt(comboGroup.getValue());
            StudentiEntity student = this.studentService.findByName(name, group);
            if (student != null) {
                String task = this.comboBoxTasks.getValue();
                String task_name = task.split("/")[0];
                TemeEntity tema = this.temaService.findByName(task_name);
                if (tema != null) {
                    String value = this.gradeField.getText();
                    LocalDate localDate = dateField.getValue();
                    String feedback = feedbackArea.getText();
                    Boolean motivated = motivatedCheckBox.isSelected();
                    //Nota nota = this.notaService.addNota(student.getID(), tema.getID(), 1, Double.parseDouble(value), localDate, feedback, motivated);
                    Parent root;
                    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/preview.fxml"));
                    root = loader.load();
                    PreviewController previewController = loader.getController();
                    previewController.setData(
                                    idUser,
                                    student,
                                    tema,
                                    localDate,
                                    Double.parseDouble(value),
                                    motivated,
                                    feedback,
                                    this.notaService);
                    Scene newScene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(newScene);
                    stage.show();
//                    if(nota != null)
//                        MessageAlert.showErrorMessage(null, "Nu se poate adauga nota!");
                }
                else {
                    MessageAlert.showErrorMessage(null, "Tema inexistenta");
                }
            }
            else {
                MessageAlert.showErrorMessage(null, "Studentul nu a fost gasit");
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getError());
        } catch (StudentPresentException e) {
            MessageAlert.showErrorMessage(null, e.getError());
        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Invalid grade/group");
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
//
//        Parent root;
//        FXMLLoader loader = null;
//        if(this.right.equals("t"))
//            loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/restrictiveMainMenu.fxml"));
//        else if(this.right.equals("a"))
//            loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/mainView.fxml"));
//
//        root = loader.load();
//        MainController controller = loader.getController();
//        controller.setMainService(notaService, studentService, temaService, accountService, this.right);
//        controller.setUser(idUser);
//        Scene newScene = new Scene(root);
//        Stage newStage = new Stage();
//        newStage.setScene(newScene);
//        newStage.show();
    }

    private void handleFilter() {
        Predicate<NotaDTO> p1 = n->n.getName().contains(filterStudentName.getText());
        Predicate<NotaDTO> p2 = n->n.getTask().contains(filterTaskName.getText());
        Predicate<NotaDTO> p3 = n->n.getDate().isAfter(filterStartDate.getValue());
        Predicate<NotaDTO> p4 = m->m.getDate().isBefore(filterFinalDate.getValue());

        if(filterStudentName.getText().equals("") && filterStudentName.getText().equals("")) {
            model.setAll(getNotaDTOList().stream()
                    .filter(p3.and(p4))
                    .collect(Collectors.toList()));
        }
        if(filterTaskName.getText().equals("")) {
            model.setAll(getNotaDTOList().stream()
                    .filter(p1.and(p3).and(p4))
                    .collect(Collectors.toList()));
        }
        else if(filterStudentName.getText().equals("")){
            model.setAll(getNotaDTOList().stream()
                    .filter(p2.and(p3).and(p4))
                    .collect(Collectors.toList()));
        }
        else {
            model.setAll(getNotaDTOList().stream()
                    .filter(p1.and(p2).and(p3).and(p4))
                    .collect(Collectors.toList()));
        }

        pagination.setPageFactory(this::createPage);
    }
    @FXML
    private void handleGroupFilter() {
        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/filterView.fxml"));
        try {
            root = loader.load();
            FilterController controller = loader.getController();
            controller.setFilterService(this.studentService,this.temaService, this.notaService, Integer.parseInt(this.filterGroupComboBox.getValue()));
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Invalid group");
        }
    }

    @FXML
    private void handleScenario() {
        Parent root;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("../UI/View/nota_sc2.fxml"));
        try {
            root = loader.load();
            NotaSc2Controller controller = loader.getController();
            controller.setNota2Service(idUser,this.notaService, this.studentService, this.temaService);
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
