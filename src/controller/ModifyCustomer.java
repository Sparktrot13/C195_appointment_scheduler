package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import model.Countries;
import model.Customers;
import model.FLDivisions;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static database.DBAppointments.deleteAppt;
import static model.Lists.*;
import static utility.Utility.*;
import static utility.Validator.*;
import static database.DBCustomers.*;

public class ModifyCustomer {
    public ComboBox<Countries> Country_Combo;
    public ComboBox<FLDivisions> Region_Combo;
    public Label Country_Label;
    public Label Region_Label;
    public Label Postal_Label;
    public Label Address_Label;
    public Label Phone_Label;
    public Label Name_Label;
    public Label ID_Label;
    public TextField ID_Cust;
    public TextField Name_Cust;
    public TextField Phone_Cust;
    public TextField Address_Cust;
    public TextField Postal_Cust;
    public Button Update_Cust;
    public TableView<Appointments> ApptTable_Cust;
    public TableColumn<Object, Object> ID_Col;
    public TableColumn<Object, Object> Title_Col;
    public TableColumn<Object, Object> Location_Col;
    public TableColumn<Object, Object> Start_Col;
    public TableColumn<Object, Object> End_Col;
    public Label TableTitle_Label;
    public Label Title_Label;
    public Button Create_Appt;
    public Button Update_Appt;
    public Button Delete_Appt;
    public Button Back_Screen;


    public void CountryCombo() {
        Countries c = Country_Combo.getSelectionModel().getSelectedItem();
        int country_ID = c.getCountry_ID();
        Region_Combo.setItems(searchCountries(country_ID));
        Region_Combo.getSelectionModel().selectFirst();
    }

    public void UpdateCust() {
        try{
            chkCustomerBlank(Name_Cust,Address_Cust,Phone_Cust,Postal_Cust,Region_Combo,Country_Combo);
            int ID = Integer.parseInt(ID_Cust.getText());
            String name = Name_Cust.getText().trim();
            String address = Address_Cust.getText().trim();
            String postal = Postal_Cust.getText().trim();
            String phone = Phone_Cust.getText().trim();
            Timestamp updated = Timestamp.valueOf(LocalDateTime.now());
            String updater = currentUser.getUser_Name();
            int regID = (Region_Combo.getSelectionModel().getSelectedItem()).getDiv_ID();
            updateCustomer(name,address,phone, postal,updated,updater,regID,ID);
        } catch (NumberFormatException e){
            alert(alertType.error,errorsFound.concat(errors.toString()),"Error");
        }
    }

    public void CreateAppt() throws IOException {
        int chkID = Integer.parseInt(ID_Cust.getText().trim());
        int index = lookupCustomer.find(chkID);
        sendCustAppt(index);
        ObservableList<Appointments> l = lookupAppts(chkID);
        ApptTable_Cust.setItems(l);
    }

    public void UpdateAppt() throws IOException {
        sendApptCust(ApptTable_Cust,Integer.parseInt(ID_Cust.getText()));
    }

    public void DeleteAppt() {
        Appointments a = ApptTable_Cust.getSelectionModel().getSelectedItem();
        if (a == null){
            alert(alertType.error,selectionError, "Selection Error");
        } else {
            deleteAppt(a);
            ApptTable_Cust.setItems(getAppts());
        }
    }

    public void BackScreen(ActionEvent actionEvent) {
        closeScreen(actionEvent);
    }

    public void populateCustomer(Customers customer) {
        ID_Cust.setText(Integer.toString(customer.getCustomer_ID()));
        Name_Cust.setText(customer.getCustomer_Name());
        Address_Cust.setText(customer.getCustomer_Address());
        Phone_Cust.setText(customer.getCustomer_Phone());
        Postal_Cust.setText(customer.getCustomer_Postal());
        int rc1 = lookupCountry(customer.getCustomer_Div_ID());
        int cs1 = countryIndex.find(rc1);
        System.out.print("Country index is: " + cs1);
        Region_Combo.setItems(searchCountries(rc1));
        Region_Combo.getSelectionModel().select(regionIndex(customer.getCustomer_Div_ID(), rc1));
        Country_Combo.setItems(getCountries());
        Country_Combo.getSelectionModel().select(cs1);
        System.out.println("Region code is: " + rc1);

        int custID = Integer.parseInt(ID_Cust.getText().trim());
        ObservableList<Appointments> l = lookupAppts(custID);
        ApptTable_Cust.setItems(l);
        ID_Col.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Title_Col.setCellValueFactory(new PropertyValueFactory<>("Appt_Title"));
        Location_Col.setCellValueFactory(new PropertyValueFactory<>("Appt_Location"));
        Start_Col.setCellValueFactory(new PropertyValueFactory<>("Appt_StartTime"));
        End_Col.setCellValueFactory(new PropertyValueFactory<>("Appt_EndTime"));
    }
}
