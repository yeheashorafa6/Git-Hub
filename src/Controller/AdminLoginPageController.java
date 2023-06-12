/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import View.AdminPage;
import View.PatientPage;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AdminLoginPageController implements Initializable {

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnBack;

    private final String adminUsername = "admin";  // admin username to enter to the system
    private final String adminPassword = "123";  // admin password to enter to the system

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void adminLogin(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
//        String username = this.txtUserName.getText();
//        String password = this.txtPassword.getText();
//
//        if (username.equals(adminUsername) && password.equals(adminPassword)) {
//            ViewManager.adminPage.changeSceneToAdminDachboardPage();
//            this.txtPassword.setText("");
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("login alert");
//            alert.setContentText("Incorrect Username Or Password!\nPlease try again..");
//            alert.showAndWait();
//        }
        String adminUsername = this.txtUserName.getText();
        String adminPassword = this.txtPassword.getText();

        ArrayList<User> allUsers = User.getAllUsers();
        boolean isLogedIn = false;
        for (User user: allUsers) {
            if (user.getRole().equals("admin")) { // if this user is patient check from his info
                if (user.getUsername().equals(adminUsername) && user.getPassword().equals(adminPassword)) {
                    isLogedIn = true;
                    PatientPage.logedInPatient = user;

                    //load patientDachboard FXML File in patientDachboard Scene
                    FXMLLoader patientDachboardLoader = new FXMLLoader(getClass().getResource("/View/Admin/AdminDashboardPage.fxml"));
                    Parent adminDachboardRoot = patientDachboardLoader.load();
                    AdminPage.adminDachboardPageScene = new Scene(adminDachboardRoot);

                    ViewManager.adminPage.changeSceneToAdminDachboardPage();
                    this.txtPassword.setText("");
                    break;
                }
            }
        }
        if (!isLogedIn) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("login alert");
            alert.setContentText("Incorrect Username Or Password!\nPlease try again..");
            alert.showAndWait();
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        ViewManager.closeAdminPage();
        ViewManager.openIndexPage();
    }

}