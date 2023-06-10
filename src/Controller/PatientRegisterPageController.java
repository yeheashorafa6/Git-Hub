/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class PatientRegisterPageController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUserName;
    @FXML
    private ToggleGroup groupGender;
    @FXML
    private ToggleGroup groupRole;
    @FXML
    private RadioButton radioMale;
    @FXML
    private RadioButton radioFemale;
    @FXML
    private RadioButton radioAdmail;
    @FXML
    private RadioButton radioPatient;
    @FXML
    private TextField txtPhone;
    @FXML
    private Button btnSignup;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void signup(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty() || txtFirstName.getText().isEmpty()
                || txtLastName.getText().isEmpty() || txtAge.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Input");
            alert.setContentText("There are 'empty' fields \nplease fill all fields.");
            alert.showAndWait();
        } else {
            String userName = txtUserName.getText();
            String password = txtPassword.getText();
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String gender = ((RadioButton) groupGender.getSelectedToggle()).getText();
            String role = ((RadioButton) groupRole.getSelectedToggle()).getText();

            User user = new User(userName, password, firstName, lastName, age, email, phone, gender, role);
            user.save();

            AdminDashboardPageController.createPatientStage.close();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Patient Creation");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Patient registered successfully!");
            successAlert.showAndWait();
        }
    }

    @FXML
    private void cancelSignup(ActionEvent event) {
        AdminDashboardPageController.createPatientStage.close();
    }

}
