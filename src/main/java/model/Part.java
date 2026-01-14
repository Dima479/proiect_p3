package model;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Part_ID;
    private String Name;
    private float Price;
    private int Quantity_in_stock;

    public Part() {}

    public Part(int Part_ID, int quantity_in_stock, String Name, float Price) {
        this.Quantity_in_stock=quantity_in_stock;
        this.Part_ID = Part_ID;
        this.Name = Name;
        this.Price = Price;
    }

    public int getPart_ID() {
        return Part_ID;
    }

    public void setPart_ID(int part_ID) {
        Part_ID = part_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getQuantity_in_stock() {
        return Quantity_in_stock;
    }

    public void setQuantity_in_stock(int quantity_in_stock) {
        Quantity_in_stock = quantity_in_stock;
    }
}
