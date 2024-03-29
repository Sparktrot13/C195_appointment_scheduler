package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.time.Month;
import java.util.stream.IntStream;
import static database.DBAppointments.*;
import static database.DBUsers.*;
import static database.DBContacts.*;
import static database.DBCustomers.*;
import static database.DBCountries.*;
import static database.DBFLDivision.*;
import static utility.Time.*;

public class Lists {
    public static ObservableList<Appointments> getAppts(){
        return getAllAppointments();
    }
    public static ObservableList<Users> getUsers(){
        return getAllUsers();
    }
    public static ObservableList<Contacts> getContacts(){
        return getAllContacts();
    }
    public static ObservableList<Customers> getCustomers(){
        return getAllCustomers();
    }
    public static ObservableList<Countries> getCountries(){
        return getAllCountries();
    }
    public static ObservableList<FLDivisions> getDivisions(){
        return getAllFLDivision();
    }
    public static int getContactID(ComboBox<Contacts> c){
        Contacts contacts = c.getSelectionModel().getSelectedItem();
        return contacts.getContact_ID();
    }
    public static int getCustID(ComboBox<Customers> c){
        Customers cust = c.getSelectionModel().getSelectedItem();
        return cust.getCustomer_ID();
    }
    public static ObservableList<FLDivisions> searchCountries(int country_ID){
        ObservableList<FLDivisions> firstLevel = FXCollections.observableArrayList();
        ObservableList<FLDivisions> gottenList = getDivisions();
        for (FLDivisions flDivisions : gottenList) {
            if (flDivisions.getDiv_Country_ID() == country_ID) {
                firstLevel.add(flDivisions);
            }
        }
        return firstLevel;
    }
    public static int lookupCountry(int regID) {
        int C_ID = 0;
        ObservableList<FLDivisions> regions = getDivisions();
        for(FLDivisions d : regions){
            if(d.getDiv_ID()== regID){
                C_ID = d.getDiv_Country_ID();
            }
        } return  C_ID;
    }
    public static int regionIndex(int regID, int cID){
        int index = 0;
        ObservableList<FLDivisions> div = searchCountries(cID);
        for(FLDivisions d : div){
            if(d.getDiv_ID()==regID){
                index = div.indexOf(d);
            }
        }return index;
    }
    public static findName lookupCust = (int i) -> getCustomers().stream().filter(c -> c.getCustomer_ID() == i).findAny().get().getCustomer_Name();
    public interface findName {
         String find(int i);
    }

    public static findAppt contactAppts =  c -> {
        ObservableList <Appointments> contactAppt = FXCollections.observableArrayList();
        Contacts con = c.getSelectionModel().getSelectedItem();
        getAppts().stream().filter(a -> a.getAppt_Contact_ID() == con.getContact_ID()).forEach(contactAppt::add);
        return contactAppt;
    };
    public interface findAppt {
        ObservableList<Appointments> find(ComboBox<Contacts> c);
    }

    public static findIndex lookupContact = (int i) -> IntStream.range(0, getContacts().size()).filter(ID -> getContacts().get(ID).getContact_ID()==i).findFirst().orElse(-1);
    public static findIndex countryIndex = (int i) -> IntStream.range(0,getCountries().size()).filter(ID -> getCountries().get(ID).getCountry_ID() == i).findFirst().orElse(-1);
    public static findIndex lookupCustomer = (int i) -> IntStream.range(0,getCustomers().size()).filter(ID -> getCustomers().get(ID).getCustomer_ID()== i).findFirst().orElse(-1);
    public interface findIndex {
        int find(int i);
    }
    public static ObservableList<Appointments> lookupAppts(int custID){
        ObservableList<Appointments> allAppts = getAppts();
        ObservableList<Appointments> custAppts = FXCollections.observableArrayList();
        for(Appointments a : allAppts){
            if (a.getAppt_Customer_ID() == custID){
                custAppts.add(a);
            }
        } return custAppts;
    }
    public static ObservableList <String> getType(){
        ObservableList getApptType = FXCollections.observableArrayList();
        for (Appointments a : getAppts()){
            if(!getApptType.contains(a.getAppt_Type().trim().toLowerCase()))
            getApptType.add(a.getAppt_Type());
        } return getApptType;
    }
    public static ObservableList<Month> getAllMonths(){
        ObservableList<Month> getTheMonths = FXCollections.observableArrayList();
        for(Month m : Month.values()){
            getTheMonths.add(m);
        } return getTheMonths;
    }
    public static ObservableList<Appointments> getMonthAppts() {
        ObservableList<Appointments> thisMonth = FXCollections.observableArrayList();
        for (Appointments a : getAppts()) {
            if (getMonth(a.getAppt_StartTime())) {
                thisMonth.add(a);
            }
        } return thisMonth;
    }
    public static ObservableList<Appointments> getWeekAppts() {
        ObservableList<Appointments> thisWeek = FXCollections.observableArrayList();
        for (Appointments a : getAppts()) {
            if (getWeek(a.getAppt_StartTime())) {
                thisWeek.add(a);
            }
        } return thisWeek;
    }
}
