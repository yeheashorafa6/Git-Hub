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
public class UpdataAppointmentController implements Initializable {

    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtDay;
    @FXML
    private TextField txtDate;
    @FXML
    private Button btnConfirmEdits;
    @FXML
    private Button btnCancel;

    private Appointment oldAppointment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oldAppointment = Controller.AdminDashboardPageController.selectedAppointmentToUpdate;

        this.txtDate.setText(oldAppointment.getAppointment_date());
        this.txtDay.setText(oldAppointment.getAppointment_day());
        this.txtTime.setText(oldAppointment.getAppointment_time());
    }

    @FXML
    private void confirmEdits(ActionEvent event) throws SQLException, ClassNotFoundException {
        //get the new data from text field's and store it in new user object
        String date = txtDate.getText();
        String day = txtDay.getText();
        String time = txtTime.getText();

        //make an new user object having this data
        Appointment newAppointment = new Appointment(date, day, time, oldAppointment.getStatus());
        //set the new user id the same as the old user
        newAppointment.setId(oldAppointment.getId());
        // update the user in database by update method
        newAppointment.update();

        //close the update stage using our global stage var in UsersManagmentController and show an alert
        AdminDashboardPageController.updateAppointmentStage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment updated");
        alert.setContentText("Appointment has been updated");
        alert.show();
    }

    @FXML
    private void cancelUpdate(ActionEvent event) {
        AdminDashboardPageController.updateAppointmentStage.close();
    }

}
