package ph.edu.dlsu.lbycpei;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AddRecordController implements Initializable {

    private CsvHandler csvHandler;


    @FXML
    private TextField adviserField;

    @FXML
    private TextField groupField;

    @FXML
    private TextField groupNumField;

    @FXML
    private TextField membersField;

    @FXML
    private TextField panelChairField;

    @FXML
    private TextField panelist1Field;

    @FXML
    private TextField panelist2Field;

    @FXML
    private TextField statusField;

    @FXML
    private TextField syField;

    @FXML
    private TextField termField;

    @FXML
    private TextField titleField;


    @FXML
    void addRecord(ActionEvent event) {
        String title = titleField.getText();
        String group = groupField.getText();
        String term = termField.getText();
        String sy = syField.getText();
        String grp_number = groupNumField.getText();
        String members = membersField.getText();
        String adviser = adviserField.getText();
        String chair_panel = panelChairField.getText();
        String panelist1 = panelist1Field.getText();
        String panelist2 = panelist2Field.getText();
        String status = statusField.getText();

        String[] record = {
                title,
                group,
                term,
                sy,
                grp_number,
                members,
                adviser,
                chair_panel,
                panelist1,
                panelist2,
                status};

        csvHandler.writeRecord(record);
        closeStage(event);
    }


    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        csvHandler = new CsvHandler();
    }
}

