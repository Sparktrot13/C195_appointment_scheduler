package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utility.Utility.*;
import static database.DBAppointments.*;

public class ApptScreenController implements Initializable {
    public TableColumn<Object, Object> Appt_IDColumn;
    public TableColumn<Object, Object> Appt_TitleColumn;
    public TableColumn<Object, Object> Appt_DescriptionColumn;
    public TableColumn<Object, Object> Appt_LocationColumn;
    public TableColumn<Object, Object> Appt_ContactColumn;
    public TableColumn<Object, Object> Appt_TypeColumn;
    public TableColumn<Object, Object> Appt_StartColumn;
    public TableColumn<Object, Object> Appt_EndColumn;
    public TableColumn<Object, Object> Appt_CustomerColumn;
    public TableColumn<Object, Object> Appt_UserColumn;
    public DatePicker Appt_DatePicker;
    public RadioButton Appt_AllRadio;
    public ToggleGroup Appt_Selections;
    public RadioButton Appt_MonthRadio;
    public RadioButton Appt_WeekRadio;
    public Button NewAppt;
    public Button UpdateAppt;
    public Button DeleteAppt;
    public Button CustomerAppt;
    public Button ExitAppt;
    public MenuBar Appt_MenuBar;
    public TableView<model.Appointments> ApptTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ApptTable.setItems(getAllAppointments());
        Appt_IDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Appt_TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_Title"));
        Appt_DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_Description"));
        Appt_LocationColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_Location"));
        Appt_TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_Type"));
        Appt_ContactColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_Contact_ID"));
        Appt_StartColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_StartTime"));
        Appt_EndColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_EndTime"));
        Appt_CustomerColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_Customer_ID"));
        Appt_UserColumn.setCellValueFactory(new PropertyValueFactory<>("Appt_User_ID"));
    }
    public void AllRadio_Appt(ActionEvent actionEvent) {
    }

    public void MonthRadio_Appt(ActionEvent actionEvent) {
    }

    public void WeekRadio_Appt(ActionEvent actionEvent) {
    }

    public void New_Appt(ActionEvent actionEvent) throws IOException {
        getLastURL = apptScreenURL;
        getLastTitle = "Main Screen";
        newScreen(actionEvent,addApptScreenURL, newApptTitle);
    }

    public void Update_Appt(ActionEvent actionEvent) throws IOException {
        getLastURL = apptScreenURL;
        getLastTitle = "Main Screen";
        newScreen(actionEvent,updateApptScreenURL,updateApptTitle );
    }

    public void Delete_Appt(ActionEvent actionEvent) {
        alert(alertType.confirmation,apptDelete + "selected" + ". " + confirm,confirmation);
        System.out.println("Delete confirmed");
    }

    public void Customer_Appt(ActionEvent actionEvent) throws IOException {
        viewScreen(actionEvent,customerScreenURL,customerTitle);
    }

    public void Appt_Exit(ActionEvent actionEvent) {
        exitProgram(actionEvent);
    }

}
