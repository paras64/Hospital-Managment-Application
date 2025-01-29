package Controllers.Patient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Controllers.Database.Queries;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DischargingPatient implements Initializable {

    @FXML
    private Button back;
    @FXML
    public TextField patId;

    Queries database = new Queries();
    Alert error = new Alert(Alert.AlertType.ERROR);
    Alert failed = new Alert(Alert.AlertType.ERROR);
    Alert info = new Alert(Alert.AlertType.INFORMATION);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error.setContentText("Please fill all the entries in correct format.");
        failed.setContentText("Failed to find id.");
        info.setContentText("Patient discharged successfully.");
    }

    // Going to the previous Page
    @FXML
    private void backToOptions(ActionEvent event) throws IOException {
        Parent patientEntry = FXMLLoader.load(getClass().getResource("../../View/startupPage.fxml"));
        back.getScene().setRoot(patientEntry);
    }

    // if ID is correct move to the Next Page
    @FXML
    private void delete() throws IOException, SQLException {

        if (confirmId(patId.getText()) || database.patientExists(Integer.parseInt(patId.getText()))) {
            try {
                database.dischargePatient(patId.getText());
                info.showAndWait();
                back.fire();
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
            failed.showAndWait();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean idExists(String id) {
        try {
            if(database.patientExists(Integer.parseInt(id))) {
                return true;
            }
            else {
                failed.showAndWait();
                return false;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            failed.showAndWait();
        }
        catch (Exception e) {
            System.out.println(e);
            failed.showAndWait();
        }
        return false;
    }

    public boolean confirmId(String id) {
        if (!isNumeric(id)) {
            error.showAndWait();
        }
        return idExists(id);
    }
}

