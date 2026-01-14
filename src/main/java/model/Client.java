package model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Client extends User {
    private String CNP;
    private String Phone_Number;

    public Client() {}

    public Client(int User_ID, String Name, String Email, String Password, String Role, String CNP, String Phone_Number) {
        super(User_ID, Name, Email, Password, Role);
        this.CNP = CNP;
        this.Phone_Number = Phone_Number;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    @Override
    public String toString() {
        return getName(); // Afiseaza numele in JComboBox
    }
}
