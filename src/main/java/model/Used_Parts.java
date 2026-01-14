package model;

import javax.persistence.*;

@Entity
@Table(name = "used_parts")
public class Used_Parts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Used_Parts_ID;
    
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation Reservation;
    
    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part Part;
    
    private int Quantity;

    public Used_Parts() {}

    public Used_Parts(int Used_Parts_ID, Reservation reservation, Part Part, int Quantity) {
        this.Used_Parts_ID = Used_Parts_ID;
        this.Reservation=reservation;
        this.Part = Part;
        this.Quantity = Quantity;
    }

    public int getUsed_Parts_ID() {
        return Used_Parts_ID;
    }

    public void setUsed_Parts_ID(int used_Parts_ID) {
        Used_Parts_ID = used_Parts_ID;
    }

    public model.Reservation getReservation() {
        return Reservation;
    }

    public void setReservation(model.Reservation reservation) {
        Reservation = reservation;
    }

    public model.Part getPart() {
        return Part;
    }

    public void setPart(model.Part part) {
        Part = part;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
