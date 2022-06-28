package ServerTickets.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="place_seats")
public class PlaceSeats implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @ManyToOne
    @JoinColumn(name="place", nullable = false)
    private Place place;
    @Column(name="type_of_seats",nullable = false)
    private String seatsType;
    @Column(name="seats_description",nullable = false)
    private String seatsDescription;
    @Column(name="number",nullable = false)
    private int number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getSeatsType() {
        return seatsType;
    }

    public void setSeatsType(String seatsType) {
        this.seatsType = seatsType;
    }

    public String getSeatsDescription() {
        return seatsDescription;
    }

    public void setSeatsDescription(String seatsDescription) {
        this.seatsDescription = seatsDescription;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
