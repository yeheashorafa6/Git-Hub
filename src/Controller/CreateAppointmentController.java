/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class CreateAppointmentController implements Initializable {

    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtDay;
    @FXML
    private TextField txtDate;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;
    @FXML
    private TextField txtStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void confirmCreate(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtTime.getText().isEmpty() || txtDate.getText().isEmpty() || txtDay.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Input");
            alert.setContentText("There are 'empty' fields \nplease fill all fields.");
            alert.showAndWait();
        } else {
            String date = txtDate.getText();
            String day = txtDay.getText();
            String time = txtTime.getText();
            String status = txtStatus.getText(); // as initial value will be 'free'

            Appointment newAppointment = new Appointment(date, day, time, status);
            newAppointment.save();

            AdminDashboardPageController.createAppointmentStage.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Creation");
            alert.setContentText("Appointment has been created");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelCreate(ActionEvent event) {
        AdminDashboardPageController.createAppointmentStage.close();
    }

}
