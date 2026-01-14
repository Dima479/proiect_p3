package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "receipts")
public class Reciept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Reciept_ID;
    private float Val;
    private LocalDate Date;
    
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation Reservation;

    public Reciept() {}

    public Reciept(int Reciept_ID, float Val, LocalDate Date, Reservation reservation) {
        this.Reservation=reservation;
        this.Reciept_ID = Reciept_ID;
        this.Val = Val;
        this.Date = Date;
    }

    public int getReciept_ID() {
        return Reciept_ID;
    }

    public void setReciept_ID(int reciept_ID) {
        Reciept_ID = reciept_ID;
    }

    public float getVal() {
        return Val;
    }

    public void setVal(float val) {
        Val = val;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public model.Reservation getReservation() {
        return Reservation;
    }

    public void setReservation(model.Reservation reservation) {
        Reservation = reservation;
    }
}
