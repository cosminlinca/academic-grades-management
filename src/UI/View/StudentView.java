//package UI.View;
//
//import Domain.Student;
//import Domain.StudentiEntity;
//import UI.Controller.StudentController;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//
//import java.io.File;
//
//public class StudentView {
//    private StudentController studentController;
//
//    private BorderPane borderPane;
//    public TableView<StudentiEntity> tableView = new TableView<>();
//
//    public StudentView(StudentController studentController) {
//        this.studentController = studentController;
//        initView();
//        listen();
//    }
//
//    public TextField textFieldId = new TextField();
//    public TextField textFieldName = new TextField();
//    public TextField textFieldEmail = new TextField();
//    public TextField textFieldGroup = new TextField();
//    public Button deleteButton = new Button("Delete");
//    public Button markAsMissingButton = new Button("Temp Del");
//    public Button markAsPresent = new Button("Present");
//    public Button back = new Button("Back");
//
//    public BorderPane getView(){ return this.borderPane;}
//
//    private void initView() {
//        this.borderPane = new BorderPane();
//        borderPane.setTop(initTop());
//        borderPane.setLeft(initLeft());
//        borderPane.setCenter(initCenter());
//        borderPane.setStyle("-fx-background-color: #dcdcdc;");
//    }
//
//    private Node initCenter() {
//        AnchorPane centerAnchorPane = new AnchorPane();
//        GridPane gridPane = createGridPane();
//        //anchor the gridpane
//        centerAnchorPane.getChildren().add(gridPane);
//        AnchorPane.setLeftAnchor(gridPane,40d);
//        AnchorPane.setTopAnchor(gridPane, 20d);
//
//        HBox buttons = createButtons();
//        //anchor the buttons
//        AnchorPane.setTopAnchor(buttons, 150d);
//       // AnchorPane.setBottomAnchor(buttons, 100d);
//        AnchorPane.setLeftAnchor(buttons, 90d);
//
//        Label label = new Label("* - optional field");
//        label.setStyle("-fx-font-style: italic; -fx-font-size: 13");
//        centerAnchorPane.getChildren().add(label);
//        AnchorPane.setTopAnchor(label, 190d);
//        AnchorPane.setLeftAnchor(label,90d);
//
//        centerAnchorPane.getChildren().add(buttons);
//        return centerAnchorPane;
//
//    }
//
//    private GridPane createGridPane() {
//        GridPane gridPaneStudent = new GridPane();
//        gridPaneStudent.setHgap(5);
//        gridPaneStudent.setVgap(5);
//
//        Label labelId = createLabel("ID ");
//        Label labelName = createLabel("Name ");
//        Label labelEmail = createLabel("Email ");
//        Label labelGroup = createLabel("Group * ");
//
//        gridPaneStudent.add(labelId,0,0);
//        gridPaneStudent.add(labelName,0,1);
//        gridPaneStudent.add(labelEmail, 0,2);
//        gridPaneStudent.add(labelGroup,0,3);
//
//        gridPaneStudent.add(textFieldId, 1, 0);
//        gridPaneStudent.add(textFieldName, 1,1);
//        gridPaneStudent.add(textFieldEmail, 1, 2);
//        gridPaneStudent.add(textFieldGroup, 1,3);
//
//        return gridPaneStudent;
//    }
//
//    private Label createLabel(String s) {
//        Label l = new Label(s);
//        l.setFont(new Font(12));
//        l.setTextFill(Color.DARKBLUE);
//        l.setStyle("-fx-font-weight: bold");
//        return l;
//    }
//
//    private Node initLeft() {
//        AnchorPane leftAnchorPane = new AnchorPane();
//        this.tableView = createStudentsTable();
//        leftAnchorPane.getChildren().add(tableView);
//        AnchorPane.setLeftAnchor(tableView, 20d);
//        AnchorPane.setTopAnchor(tableView, 20d);
//
//        HBox hBoxButtons = createDelButtons();
//        AnchorPane.setBottomAnchor(hBoxButtons, 40d);
//        AnchorPane.setLeftAnchor(hBoxButtons, 110d);
//        leftAnchorPane.getChildren().add(hBoxButtons);
//
//
//        return leftAnchorPane;
//    }
//
//    private HBox createDelButtons() {
//        markAsMissingButton.setDisable(true);
//        markAsMissingButton.setStyle("-fx-background-color: #150578; -fx-text-fill: white;");
//        deleteButton.setDisable(true);
//        deleteButton.setStyle("-fx-background-color: #150578; -fx-text-fill: white;");
//        markAsPresent.setDisable(true);
//        markAsPresent.setStyle("-fx-background-color: #150578; -fx-text-fill: white;");
//
//        HBox hBox = new HBox(5, deleteButton, markAsMissingButton, markAsPresent);
//        deleteButton.setOnAction(this.studentController::handleDeleteStudent);
//        markAsMissingButton.setOnAction(this.studentController::handleTempDelStudent);
//        markAsPresent.setOnAction(this.studentController::handlePresent);
//        return hBox;
//    }
//
//    private HBox createButtons() {
//        Button addButton = new Button("Add");
//        addButton.setStyle("-fx-background-color: #150578; -fx-text-fill: white;");
//        Button updateButton = new Button("Update");
//        updateButton.setStyle("-fx-background-color: #150578; -fx-text-fill: white;");
//
//        HBox hBox = new HBox(4,addButton, updateButton);
//        addButton.setOnAction(this.studentController::handleAddStudent);
//        updateButton.setOnAction(this.studentController::handleUpdateStudent);
//
//        return hBox;
//    }
//
//    private TableView<StudentiEntity> createStudentsTable() {
//        TableColumn<StudentiEntity, Integer> idColumn = new TableColumn<>("ID");
//        TableColumn<StudentiEntity, String> nameColumn = new TableColumn<>("NAME");
//        TableColumn<StudentiEntity, String> emailColumn = new TableColumn<>("EMAIL");
//        TableColumn<StudentiEntity, Integer> groupColumn = new TableColumn<>("GROUP");
//        TableColumn<StudentiEntity, Boolean> presentColumn = new TableColumn<>("PRESENT");
//
//        //The CONSTRAINED_RESIZE_POLICY will remove the extra column
//        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        this.tableView.getColumns().addAll(idColumn, nameColumn, emailColumn, groupColumn, presentColumn);
//
//        //render data
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
//        groupColumn.setCellValueFactory(new PropertyValueFactory<>("grupa"));
//        presentColumn.setCellValueFactory(new PropertyValueFactory<>("prezent"));
//
//        //bind data
//        this.tableView.setItems(this.studentController.getModel());
//
//
//        //addLister
//        this.tableView.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> {
//
//
//                     this.studentController.showMessageTaskDetails(newValue);
//                     textFieldId.setEditable(false);});
//        return this.tableView;
//    }
//
//    private Node initTop() {
//        AnchorPane topAnchorPane = new AnchorPane();
//        topAnchorPane.setStyle("-fx-background-color:  #008B8B");
////        ImageView imageView = new ImageView(new Image(new File("C:\\Users\\Coss\\IdeaProjects\\CATALOG\\src\\UI\\View\\images\\back.png").toURI().toString()));
////        imageView.setFitWidth(50);
////        imageView.setFitHeight(50);
////
////        topAnchorPane.getChildren().add(imageView);
////        topAnchorPane.setTopAnchor(imageView, 10d);
////        topAnchorPane.setLeftAnchor(imageView, 30d);
////        topAnchorPane.setBottomAnchor(imageView, 10d);
//
//        back.setStyle("-fx-font-size: 15; -fx-background-color: #150578; -fx-text-fill: #FFFFFF");
//        topAnchorPane.getChildren().add(back);
//        topAnchorPane.setTopAnchor(back, 25d);
//        topAnchorPane.setLeftAnchor(back, 20d);
//        topAnchorPane.setBottomAnchor(back, 10d);
//        back.setOnAction(this.studentController::handleGoBack);
//
//        return topAnchorPane;
//
//    }
//
//    /**
//     * add listener for buttons
//     */
//    private void listen() {
//        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if(tableView.getSelectionModel().getSelectedItem() != null) {
//                StudentiEntity student = tableView.getSelectionModel().getSelectedItem();
//                if(student.isPrezent() == true) {
//                    deleteButton.setDisable(false);
//                    markAsMissingButton.setDisable(false);
//                    markAsPresent.setDisable(true);
//                }
//                else {
//                    deleteButton.setDisable(false);
//                    markAsMissingButton.setDisable(true);
//                    markAsPresent.setDisable(false);
//                }
//            }
//        });
//    }
//
//
//}
