package ServerTickets.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="event")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="name",nullable = false, length = 30)
    private String name;
    @Column(name="type",nullable = false,length = 20)
    private String type;
    @Column(name="description",nullable = false, length = 30)
    private String description;
    @Column(name="date",nullable = false)
    private Date date;
    @ManyToOne
    @JoinColumn(name="organizer",nullable = false)
    private Customer organizer;

    public Customer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Customer customer) {
        this.organizer = customer;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
