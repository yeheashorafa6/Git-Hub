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
public class UpdataPatientController implements Initializable {

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
    private Button btnConfirmEdits;
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
    private ToggleGroup groupGender;
    @FXML
    private ToggleGroup groupRole;
    @FXML
    private Button btnCancel;

    private User oldUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oldUser = Controller.AdminDashboardPageController.selectedUserToUpdate;

        this.txtUserName.setText(oldUser.getUsername());
        this.txtPassword.setText(oldUser.getPassword());
        this.txtFirstName.setText(oldUser.getFirstName());
        this.txtLastName.setText(oldUser.getLastName());
        this.txtAge.setText(String.valueOf(oldUser.getAge()));
        this.txtEmail.setText(oldUser.getEmail());
        this.txtPhone.setText(oldUser.getPhone());

        if (oldUser.getGender().equals("female")) {
            this.groupGender.selectToggle(radioFemale);
        }
        if (oldUser.getRole().equals("patient")) {
            this.groupRole.selectToggle(radioPatient);
        }
    }

    @FXML
    private void confirmEdits(ActionEvent event) throws SQLException, ClassNotFoundException {
        //get the new data from text field's and store it in new user object
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        int age = Integer.parseInt(txtAge.getText());
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String gender = ((RadioButton) groupGender.getSelectedToggle()).getText();
        String role = ((RadioButton) groupRole.getSelectedToggle()).getText();

        //make an new user object having this data
        User newUser = new User(username, password, firstName, lastName, age, email, phone, gender, role);

        //set the new user id the same as the old user
        newUser.setId(oldUser.getId());

        // update the user in database by update method
        newUser.update();

        //close the update stage using our global stage var in UsersManagmentController and show an alert
        AdminDashboardPageController.updatePatientStage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User updated");
        alert.setContentText("User has been updated");
        alert.show();
    }

    @FXML
    private void cancelUpdate(ActionEvent event) {
        AdminDashboardPageController.updatePatientStage.close();
    }

}
