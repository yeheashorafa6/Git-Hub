package Controller;

import static Controller.AdminDashboardPageController.createPatientStage;
import Model.User;
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
import javafx.stage.Stage;

public class PatientLoginPageController implements Initializable {

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnBack;

    public static User logedInPatient;  // the patient currently has beed loged in
    @FXML
    private Button btnPatientRegister;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void patientLogin(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String patientUsername = this.txtUserName.getText();
        String patientPassword = this.txtPassword.getText();

        ArrayList<User> allUsers = User.getAllUsers();
        boolean isLogedIn = false;
        for (User user : allUsers) {
            if (user.getRole().equals("patient")) { // if this user is patient check from his info
                if (user.getUsername().equals(patientUsername) && user.getPassword().equals(patientPassword)) {
                    isLogedIn = true;
                    PatientPage.logedInPatient = user;

                    //load patientDachboard FXML File in patientDachboard Scene
                    FXMLLoader patientDachboardLoader = new FXMLLoader(getClass().getResource("/View/Admin/PatientDashboardPage.fxml"));
                    Parent patientDachboardRoot = patientDachboardLoader.load();
                    PatientPage.patientDachboardPageScene = new Scene(patientDachboardRoot);

                    ViewManager.patientPage.changeSceneToPatientDachboardPage();
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
        ViewManager.closePatientPage();
        ViewManager.openIndexPage();
    }

    @FXML
    private void registerPatient(ActionEvent event) throws IOException {
        FXMLLoader loaderUpdate = new FXMLLoader(getClass().getResource("/View/Admin/PatientRegisterPage.fxml"));
        Parent rootcreate = loaderUpdate.load();
        Scene createUserScene = new Scene(rootcreate);

        createPatientStage = new Stage();
        createPatientStage.setScene(createUserScene);
        createPatientStage.setTitle("Create New Patient");
        createPatientStage.showAndWait();
    }

}
