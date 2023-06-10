package Controller;

import Model.Appointment;
import Model.Booked_Appointment;
import View.PatientPage;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PatientDashboardPageController implements Initializable {

    @FXML
    private Button btnShowAllBookedWaitingAppointments;
    @FXML
    private Button btnShowAllAppointments;
    @FXML
    private Button btnShowAllBookedFinishedAppointments;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnBookedApp;
    @FXML
    public Label lablePatientUN;
    @FXML
    public Label lablePatientID;

    @FXML
    private TableView<Appointment> freeTableView;
    @FXML
    private TableColumn<Appointment, Integer> idCol;
    @FXML
    private TableColumn<Appointment, String> appDateCol;
    @FXML
    private TableColumn<Appointment, String> appDayCol;
    @FXML
    private TableColumn<Appointment, String> appTimeCol;
    @FXML
    private TableColumn<Appointment, String> statusCol;

    @FXML
    private TableView<Booked_Appointment> bookedTableView;
    @FXML
    private TableColumn<Booked_Appointment, Integer> idBookedCol;
    @FXML
    private TableColumn<Booked_Appointment, Integer> userIdCol;
    @FXML
    private TableColumn<Booked_Appointment, Integer> appIdCol;
    @FXML
    private TableColumn<Booked_Appointment, String> statusBookedCol;
    @FXML
    private TableColumn<Booked_Appointment, String> docCommentCol;

    private boolean isLightMode;
    @FXML
    private AnchorPane parent;
    @FXML
    private Button btnMode;
    @FXML
    private ImageView imgMode;
    @FXML
    private Label mode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // To make Maping between (freeTableView column's) and (Appointment) object attributes
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        appDateCol.setCellValueFactory(new PropertyValueFactory("appointment_date"));
        appDayCol.setCellValueFactory(new PropertyValueFactory("appointment_day"));
        appTimeCol.setCellValueFactory(new PropertyValueFactory("appointment_time"));
        statusCol.setCellValueFactory(new PropertyValueFactory("status"));

        // To make Maping between (bookedTableView column's) and (Booked_Appointment) object attributes
        idBookedCol.setCellValueFactory(new PropertyValueFactory("id"));
        userIdCol.setCellValueFactory(new PropertyValueFactory("user_id"));
        appIdCol.setCellValueFactory(new PropertyValueFactory("appointment_id"));
        statusBookedCol.setCellValueFactory(new PropertyValueFactory("status"));
        docCommentCol.setCellValueFactory(new PropertyValueFactory("doctor_commnet"));

        this.lablePatientID.setText(String.valueOf(PatientPage.logedInPatient.getId()));
        this.lablePatientUN.setText(PatientPage.logedInPatient.getUsername());
    }

    @FXML
    private void ShowAllFreeAppointments(ActionEvent event) throws SQLException, ClassNotFoundException {
        ArrayList<Appointment> freeAppointments = new ArrayList();
        ArrayList<Appointment> allAppointments = Appointment.getAllAppointments();
        for (Appointment a : allAppointments) {
            if (a.getStatus().equals("free")) {
                freeAppointments.add(a);
            }
        }
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(freeAppointments);
        freeTableView.setItems(appointmentList);
    }

    @FXML
    private void bookAppointment(ActionEvent event) throws SQLException, ClassNotFoundException {

        //check if there is an Appointment selected from the feeTableView
        if (freeTableView.getSelectionModel().getSelectedItem() != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Booking Confirmation");
            alert.setContentText("Are you sure you want to book this Appointment?");

            // when the confirmation alert displayed, it waint you to answar (OK or Cancel)
            alert.showAndWait().ifPresent(ans -> {
                if (ans == ButtonType.OK) {
                    // update the selected appointment from the freeTableView(free appointments)
                    Appointment selectedAppiontemnt = freeTableView.getSelectionModel().getSelectedItem();
                    selectedAppiontemnt.setStatus("booked");

                    Booked_Appointment newBookedAppointment = new Booked_Appointment(PatientPage.logedInPatient.getId(),
                            selectedAppiontemnt.getId(), "waiting", "no-comment");
                    try {
                        selectedAppiontemnt.update(); // To make it updated in database also
                        newBookedAppointment.save(); // To Add the new Booked Appointment to the Booked_Appointment Table
                    } catch (SQLException ex) {
                        Logger.getLogger(PatientDashboardPageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PatientDashboardPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            ShowAllFreeAppointments(new ActionEvent());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setContentText("You should select an appointment to be booked!");
            alert.showAndWait();
        }
    }

    @FXML
    private void ShowAllBookedWaitingAppointments(ActionEvent event) throws SQLException, ClassNotFoundException {
        ArrayList<Booked_Appointment> allBookedAppointments = Booked_Appointment.getAllBookedAppointments();
        ArrayList<Booked_Appointment> patientBookedWaitingAppoinments = new ArrayList();

        for (Booked_Appointment ba : allBookedAppointments) {
            if (ba.getStatus().equals("waiting") && ba.getUser_id() == PatientPage.logedInPatient.getId()) { //  && ba.getUser_id() == PatientPage.logedInPatientID
                patientBookedWaitingAppoinments.add(ba);
            }
        }
        ObservableList<Booked_Appointment> bookedAppointmentList
                = FXCollections.observableArrayList(patientBookedWaitingAppoinments);
        bookedTableView.setItems(bookedAppointmentList);
    }

    @FXML
    private void ShowAllBookedFinishedAppointments(ActionEvent event) throws SQLException, ClassNotFoundException {
        ArrayList<Booked_Appointment> allBookedAppointments = Booked_Appointment.getAllBookedAppointments();
        ArrayList<Booked_Appointment> patientBookedWaitingAppoinments = new ArrayList();

        for (Booked_Appointment ba : allBookedAppointments) {
            if (ba.getStatus().equals("finished") && ba.getUser_id() == PatientPage.logedInPatient.getId()) { //  && ba.getUser_id() == PatientPage.logedInPatientID
                patientBookedWaitingAppoinments.add(ba);
            }
        }
        ObservableList<Booked_Appointment> bookedAppointmentList
                = FXCollections.observableArrayList(patientBookedWaitingAppoinments);
        bookedTableView.setItems(bookedAppointmentList);
    }

    @FXML
    private void showBtnMode(ActionEvent event) {
        isLightMode = !isLightMode;
        if (isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    private void setLightMode() {
        parent.getStylesheets().remove("/css/patientdashboardpageDarkMode.css");
        parent.getStylesheets().add("/css/patientdashboardpageLightMode.css");
        Image image = new Image("/image/dark.png");
        imgMode.setImage(image);
        mode.setText("Dark Mode");

        isLightMode = true;
    }

    private void setDarkMode() {
        parent.getStylesheets().remove("/css/patientdashboardpageLightMode.css");
        parent.getStylesheets().add("/css/patientdashboardpageDarkMode.css");
        Image image = new Image("/image/light.png");
        imgMode.setImage(image);
        mode.setText("Light Mode");

        isLightMode = false;
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setContentText("Are you sure you want to Logout?");
        alert.showAndWait().ifPresent(ans -> {
            if (ans == ButtonType.OK) {
                ViewManager.patientPage.changeSceneToPatientLoginPage();
            }
        });
    }

}
