package ServerTickets.entities;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="place")
public class Place implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="name",nullable = false, length = 30)
    private String name;
    @Column(name="address",nullable = false, length = 50)
    private String address;
    @Column(name="description",nullable = true)
    private String description;

    public Place() { }

    public Place(String name, String address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
