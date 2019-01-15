package UI.Controller;
import Domain.TemeEntity;
import Service.TemaService;
import Utils.Observer;
import Utils.TemaChangeEvent;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TemaController implements Observer<TemaChangeEvent>{

    private TemaService temaService;
    private Stage parentStage;

    private ObservableList<TemeEntity> model = FXCollections.observableArrayList();

    @FXML
    private TableView<TemeEntity> tableView;
    @FXML
    private TableColumn<TemeEntity, Integer> tableColumnID;
    @FXML
    private TableColumn<TemeEntity, String> tableColumnDescr;
    @FXML
    private TableColumn<TemeEntity, Integer> tableColumnDeadline;
    @FXML
    private TableColumn<TemeEntity, Integer> tableColumnReceive;
    @FXML
    private Pane back;
    private final static int rowPerPage = 6;

    @Override
    public void update(TemaChangeEvent temaChangeEvent) {
        Iterable<TemeEntity> iterable = () -> this.temaService.getTemaIterator();
        model.setAll(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()));

        pagination.setPageFactory(this::createPage);
    }
    public void setTemaService(TemaService temaService, Stage stage) {
        this.temaService = temaService;
        this.parentStage = stage;
        temaService.addObserver(this);
        initModel();
    }

    private void initModel() {
        Iterable<TemeEntity> iterable = () -> this.temaService.getTemaIterator();
        model.setAll(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()));
    }


    @FXML
    public void initialize() {
        this.tableColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.tableColumnDescr.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        this.tableColumnDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        this.tableColumnReceive.setCellValueFactory(new PropertyValueFactory<>("primire"));

        //this.tableView.setItems(model);
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

    @FXML
    private TextField idField;
    @FXML
    private TextField descrField;
    @FXML
    private TextField deadlineField;
    @FXML
    private TextField receiveField;
    @FXML
    private Pagination pagination;

    @FXML
    public void handleAddTema(ActionEvent actionEvent) {
        try
        {
            Integer id = Integer.parseInt(idField.getText());
            String descr = descrField.getText();
            Integer deadline = Integer.parseInt(deadlineField.getText());
            Integer receive = Integer.parseInt(receiveField.getText());

            try {
                TemeEntity saved = this.temaService.addTema(id, descr, deadline, receive);
                if(saved == null) {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Added", "Task added!");
                }
                else MessageAlert.showErrorMessage(null, "Task already exists");
            }catch (ValidationException e){
                MessageAlert.showErrorMessage(null,e.getError());
            }
        }catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Invalid data input!");
        }
    }

    @FXML
    public void handleExtendTask(ActionEvent actionEvent) {
        TemeEntity tema = tableView.getSelectionModel().getSelectedItem();
        if(tema != null) {
            int valid = this.temaService.extendsDeadlineWithOneWeek(tema.getID());
            if(valid == 1) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Extend task", "Task with ID:" + tema.getID() + " extended!");
            }
            else MessageAlert.showErrorMessage(null,"Extending error");
        }
        else {
            MessageAlert.showErrorMessage(null, "Please select a task from table!");
        }

    }
    @FXML
    public void handleGoBack() {
        setStage(back, parentStage);
    }

    @SuppressWarnings("Duplicates")
    void setStage(Pane back, Stage parentStage) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();

        parentStage.show();

    }
}
