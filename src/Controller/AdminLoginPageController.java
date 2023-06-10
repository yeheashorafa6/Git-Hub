/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ViewManager;
import java.io.IOException;
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
    private void adminLogin(ActionEvent event) throws SQLException, ClassNotFoundException {
        String username = this.txtUserName.getText();
        String password = this.txtPassword.getText();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            ViewManager.adminPage.changeSceneToAdminDachboardPage();
            this.txtPassword.setText("");
        } else {
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
