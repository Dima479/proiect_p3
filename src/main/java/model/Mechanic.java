package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mechanics")
public class Mechanic extends User {
    private double Experience;
    private double Rating;

    @OneToMany(mappedBy = "Mechanic")
    private List<Reservation> Reservations;

    public Mechanic() {}

    public Mechanic(int User_ID, String Name, String Email, String Password, String Role, double Experience) {
        super(User_ID, Name, Email, Password, Role);
        this.Experience = Experience;
        this.Rating = 0;
        this.Reservations = new ArrayList<Reservation>();
    }

    public double getExperience() {
        return Experience;
    }

    public void setExperience(double experience) {
        Experience = experience;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public List<Reservation> getReservations() {
        return Reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        Reservations = reservations;
    }
}
