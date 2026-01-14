package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Reservation_ID;
    
    @ManyToOne
    @JoinColumn(name = "car_id") // Legatura catre masina
    private Car car;

    private LocalDate Date;
    
    @Enumerated(EnumType.STRING)
    private Status Status;
    
    @OneToMany(mappedBy = "Reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Used_Parts> Parts = new ArrayList<>();
    
    private String Details;
    
    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic Mechanic;

    public enum Status{
        PLANNED, IN_PROGRESS, COMPLETED, PENDING_PARST, CANCELLED
    }

    public Reservation() {}

    // Constructor simplificat pentru creare
    public Reservation(Car car, LocalDate date, String details, Mechanic mechanic) {
        this.car = car;
        this.Date = date;
        this.Details = details;
        this.Mechanic = mechanic;
        this.Status = Status.PLANNED;
    }

    // Getters and Setters
    public int getReservation_ID() { return Reservation_ID; }
    public void setReservation_ID(int reservation_ID) { Reservation_ID = reservation_ID; }
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
    public LocalDate getDate() { return Date; }
    public void setDate(LocalDate date) { Date = date; }
    public Status getStatus() { return Status; }
    public void setStatus(Status status) { Status = status; }
    public List<Used_Parts> getParts() { return Parts; }
    public void setParts(List<Used_Parts> parts) { this.Parts = parts; }
    public String getDetails() { return Details; }
    public void setDetails(String details) { this.Details = details; }
    public Mechanic getMechanic() { return Mechanic; }
    public void setMechanic(Mechanic mechanic) { this.Mechanic = mechanic; }
}
