package ServerTickets.entities;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="event_place_seat_price")
public class EventPlaceSeatsPrice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="event",nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name="place_seats",nullable = false)
    private PlaceSeats placeSeats;
    @Column(name="price",nullable = true)
    private int price;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public PlaceSeats getPlaceSeats() {
        return placeSeats;
    }

    public void setPlaceSeats(PlaceSeats placeSeats) {
        this.placeSeats = placeSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
