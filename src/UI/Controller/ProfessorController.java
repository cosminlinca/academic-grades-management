package UI.Controller;

import Domain.ProfesoriEntity;
import Service.ProfesorService;
import Utils.Observer;
import Utils.ProfessorChangeEvent;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProfessorController implements Observer<ProfessorChangeEvent> {
    private ProfesorService profesorService;
    private Stage parentStage;
    private ObservableList<ProfesoriEntity> model = FXCollections.observableArrayList();


    public void setProfessorService(ProfesorService profesorService, Stage stage) {
        this.profesorService = profesorService;
        this.parentStage = stage;
        profesorService.addObserver(this);
        initModel();
    }

    private void initModel() {
        Iterable<ProfesoriEntity> iterable = () -> this.profesorService.getProfessorIterator();
        model.setAll(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()));
    }

    @Override
    public void update(ProfessorChangeEvent professorChangeEvent) {
        Iterable<ProfesoriEntity> iterable = () -> this.profesorService.getProfessorIterator();
        model.setAll(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()));

        pagination.setPageFactory(this::createPage);
    }

    @FXML
    private TableView<ProfesoriEntity> tableView;
    @FXML
    private TableColumn<ProfesoriEntity, Integer> idColumn;
    @FXML
    private TableColumn<ProfesoriEntity, String> nameColumn;
    @FXML
    private TableColumn<ProfesoriEntity, String> domainColumn;
    @FXML
    private TableColumn<ProfesoriEntity, String> emailColumn;
    @FXML
    private Pane back;
    @FXML
    private TextField nameField;
    @FXML
    private TextField domainField;
    @FXML
    private TextField emailField;
    @FXML
    private Pagination pagination;

    private final static int rowPerPage = 10;

    @FXML
    private void initialize() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        this.domainColumn.setCellValueFactory(new PropertyValueFactory<>("domeniu"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

       // this.tableView.setItems(model);
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
    private void handleAdd() {
        String name = this.nameField.getText();
        String domain = this.domainField.getText();
        String email = this.emailField.getText();
        try {
            ProfesoriEntity profesoriEntity = this.profesorService.addProfesor(name, domain, email);
            if(profesoriEntity == null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Saved", "Professor saved");

        }catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getError());
        }
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();

        parentStage.show();
    }


}

