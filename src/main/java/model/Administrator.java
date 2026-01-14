package model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrators")
public class Administrator extends User {
    public Administrator() {}

    public Administrator(int User_ID, String Name, String Email, String Password, String Role) {
        super(User_ID, Name, Email, Password, Role);
    }
}
