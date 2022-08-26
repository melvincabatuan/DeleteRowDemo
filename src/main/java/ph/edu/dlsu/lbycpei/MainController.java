package ph.edu.dlsu.lbycpei;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    private CsvHandler csvHandler;

    private ArrayList<String> selectedIDs;
    @FXML
    private TableView<ThesisRecord> tableView;

    @FXML
    TableColumn<ThesisRecord, String> titleColumn;

    @FXML
    TableColumn<ThesisRecord, String> groupColumn;

    @FXML
    TableColumn<ThesisRecord, String> trmColumn;

    @FXML
    TableColumn<ThesisRecord, String> syColumn;

    @FXML
    TableColumn<ThesisRecord, String> noColumn;

    @FXML
    TableColumn<ThesisRecord, String> membersColumn;

    @FXML
    TableColumn<ThesisRecord, String> adviserColumn;

    @FXML
    TableColumn<ThesisRecord, String> chairColumn;

    @FXML
    TableColumn<ThesisRecord, String> panelist1Column;

    @FXML
    TableColumn<ThesisRecord, String> panelist2Column;

    @FXML
    TableColumn<ThesisRecord, String> statusColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println(System.getProperty("user.dir")); // Viewing current path

        // Associate columns to data
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        trmColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        syColumn.setCellValueFactory(new PropertyValueFactory<>("sy"));
        noColumn.setCellValueFactory(new PropertyValueFactory<>("grp_number"));
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("members"));
        adviserColumn.setCellValueFactory(new PropertyValueFactory<>("adviser"));
        chairColumn.setCellValueFactory(new PropertyValueFactory<>("chair_panel"));
        panelist1Column.setCellValueFactory(new PropertyValueFactory<>("panelist1"));
        panelist2Column.setCellValueFactory(new PropertyValueFactory<>("panelist2"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        csvHandler = new CsvHandler();
        tableView.getItems().setAll(csvHandler.getDatabase());
        initTableView();
    }

    private void initTableView() {
        selectedIDs = new ArrayList<>();
        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ObservableList<ThesisRecord> selectedItems = tableView.getSelectionModel().getSelectedItems();
                for (ThesisRecord row : selectedItems) {
                    selectedIDs.add(row.getTitle());
                }
                // Test print selected IDs
                for (String selectedID : selectedIDs) {
                    System.out.println(selectedID);
                }
            }
        });
    }


    public void addRecord() throws IOException {
        // Open a pop-up window, then return data
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-record-view.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent, 1024, 800);
        scene.getStylesheets().add("stylesheet.css");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        onRefreshClick();
    }

    public void onRefreshClick() {
        csvHandler.loadCsv();
        tableView.getItems().setAll(csvHandler.getDatabase());
    }

    public void deleteRecord() throws IOException {
        System.out.println("Delete Clicked!!!");
        // Get selected row
        if (!selectedIDs.isEmpty()) {
            System.out.println("ID Selected!!!");
            // Delete from csv file
            csvHandler.deleteSelected(selectedIDs);
            // Refresh TableView
            onRefreshClick();
        }
    }

}