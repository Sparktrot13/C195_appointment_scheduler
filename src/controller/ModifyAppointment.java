package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Appointments;
import model.Contacts;
import model.Customers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static database.DBAppointments.*;
import static utility.Utility.*;
import static utility.Time.*;
import static model.Lists.*;
import static utility.Validator.chkApptBlank;
import static utility.Validator.chkCollision;

public class ModifyAppointment{


    public Label ID_addApptLabel;
    public Label Title_addApptLabel;
    public Label Description_addApptLabel;
    public Label Location_addApptLabel;
    public Label Contact_addApptLabel;
    public Label Type_addApptLabel;
    public Label StartDate_addApptLabel;
    public Label StartTime_addApptLabel;
    public Label EndDate_addApptLabel;
    public Label EndTime_addApptLabel;
    public Label CustomerID_addApptLabel;
    public DatePicker Start_addApptDate;
    public DatePicker End_addApptDate;
    public ComboBox Start_addApptCombo;
    public ComboBox End_addApptCombo;
    public TextField ID_addApptTextfield;
    public TextField Title_addApptTextfield;
    public TextField Description_addApptTextfield;
    public TextField Location_addApptTextfield;
    public TextField Type_addApptTextfield;
    public ComboBox<Contacts> Contact_Combo;
    public ComboBox<Customers> Cust_Combo;
    public Label ScreenTitle_ApptLabel;
    public Button Save_addApptButton;
    public Button Cancel_addApptButton;

    public void Save_addApptButton(ActionEvent actionEvent) {
        try {
            chkApptBlank(Title_addApptTextfield, Description_addApptTextfield, Location_addApptTextfield, Type_addApptTextfield, Start_addApptDate, Start_addApptCombo, End_addApptDate, End_addApptCombo, Cust_Combo, Contact_Combo);
            int appt = Integer.parseInt(ID_addApptTextfield.getText());
            String title = Title_addApptTextfield.getText().trim();
            String Des = Description_addApptTextfield.getText().trim();
            String Loc = Location_addApptTextfield.getText().trim();
            String type = Type_addApptTextfield.getText().trim();
            Timestamp start = convertTime(combineDateTime(Start_addApptDate,Start_addApptCombo), ZoneId.systemDefault(),ZoneId.of("UTC"));
            Timestamp end = convertTime(combineDateTime(End_addApptDate,End_addApptCombo),ZoneId.systemDefault(),ZoneId.of("UTC"));
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            int cust_ID = getCustID(Cust_Combo);
            int contact_ID = getContactID(Contact_Combo);
            String updatedBy = currentUser.getUser_Name();
            int user_ID = currentUser.getUser_ID();
            if (!chkCollision(cust_ID, combineDateTime(Start_addApptDate,Start_addApptCombo),combineDateTime(End_addApptDate,End_addApptCombo))){
                errors.add(lookupCust.find(cust_ID) + " " + apptStartCollides);
                throw new NumberFormatException();
            }
            UpdateAppt(appt,title,Des,Loc,type,start,end,lastUpdate,updatedBy,cust_ID,user_ID,contact_ID);
            closeScreen(actionEvent);
        } catch (NumberFormatException d){
            System.out.println(d);
            alert(alertType.error,errorsFound.concat(errors.toString()),"Error");
        }
    }

    public void Cancel_addApptButton(ActionEvent actionEvent) throws IOException {
        closeScreen(actionEvent);
    }

    public void populateAppt(Appointments appt) {
        DateTimeFormatter combo = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime st = LocalTime.parse(appt.getAppt_StartTime().toLocalTime().format(combo));
        LocalTime et = LocalTime.parse(appt.getAppt_EndTime().toLocalTime().format(combo));
        LocalDate start = appt.getAppt_StartTime().toLocalDate();
        LocalDate end = appt.getAppt_EndTime().toLocalDate();
        int startTime = lookupTime(st);
        int endTime = lookupTime(et);
        System.out.println(startTime);
        int cust = appt.getAppt_Customer_ID();
        int contact = appt.getAppt_Contact_ID();
        int c = lookupContact.find(contact);
        int customers = lookupCustomer.find(cust);
        Start_addApptCombo.setItems(getBusinessHours());
        End_addApptCombo.setItems(getBusinessHours());
        Contact_Combo.setItems(getContacts());
        Cust_Combo.setItems(getCustomers());
        ID_addApptTextfield.setText(Integer.toString(appt.getAppointment_ID()));
        Title_addApptTextfield.setText(appt.getAppt_Title());
        Description_addApptTextfield.setText(appt.getAppt_Description());
        Location_addApptTextfield.setText(appt.getAppt_Location());
        Type_addApptTextfield.setText(appt.getAppt_Type());
        Start_addApptCombo.getSelectionModel().select(startTime);
        End_addApptCombo.getSelectionModel().select(endTime);
        Start_addApptDate.setValue(start);
        End_addApptDate.setValue(end);
        Contact_Combo.getSelectionModel().select(c);
        Cust_Combo.getSelectionModel().select(customers);


    }

    public void StartAppt_Date(ActionEvent actionEvent) {
        if(End_addApptDate.getValue() == null||End_addApptDate.getValue().isBefore(Start_addApptDate.getValue())){
            End_addApptDate.setValue(Start_addApptDate.getValue());
        }
    }

    public void StartAppt_Combo(ActionEvent actionEvent) {
        LocalTime start = LocalTime.parse(Start_addApptCombo.getSelectionModel().getSelectedItem().toString());
        if(End_addApptCombo.getSelectionModel().getSelectedItem()==null){
            End_addApptCombo.getSelectionModel().select(lookupTime(start.plusMinutes(15)));
        } else if(LocalTime.parse(End_addApptCombo.getSelectionModel().getSelectedItem().toString()).isBefore(start.plusMinutes(1))){
            End_addApptCombo.getSelectionModel().select(lookupTime(LocalTime.parse(Start_addApptCombo.getSelectionModel().getSelectedItem().toString()).plusMinutes(15)));
        }
    }
}
