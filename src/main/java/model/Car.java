package model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Car_ID;
    
    private int ownerID;
    
    private String Licence_Plate;
    private String Brand;
    private String Model;
    private int Year;
    private String VIN;

    public Car() {}

    public Car(int car_ID, int ownerID, String licence_Plate, String brand, String model, int year, String VIN) {
        this.Car_ID = car_ID;
        this.ownerID = ownerID;
        this.Licence_Plate = licence_Plate;
        this.Brand = brand;
        this.Model = model;
        this.Year = year;
        this.VIN = VIN;
    }

    @Override
    public String toString() {
        return Brand + " " + Model + " (" + Licence_Plate + ")";
    }

    // --- Getters and Setters ---
    public int getCar_ID() { return Car_ID; }
    public void setCar_ID(int car_ID) { Car_ID = car_ID; }
    public int getOwnerID() { return ownerID; }
    public void setOwnerID(int ownerID) { this.ownerID = ownerID; }
    public String getLicence_Plate() { return Licence_Plate; }
    public void setLicence_Plate(String licence_Plate) { this.Licence_Plate = licence_Plate; }
    public String getBrand() { return Brand; }
    public void setBrand(String brand) { Brand = brand; }
    public String getModel() { return Model; }
    public void setModel(String model) { this.Model = model; }
    public int getYear() { return Year; }
    public void setYear(int year) { this.Year = year; }
    public String getVIN() { return VIN; }
    public void setVIN(String VIN) { this.VIN = VIN; }
}
