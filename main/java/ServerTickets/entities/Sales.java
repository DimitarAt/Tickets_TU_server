package ServerTickets.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="event_seat_price",nullable = false)
    private EventPlaceSeatsPrice eventSeatPrice;
    @ManyToOne
    @JoinColumn(name="buyer",nullable = false)
    private TicketsBuyer buyer;
    @ManyToOne
    @JoinColumn(name="event_distributor", nullable = false)
    private EventDistributor distributor;
    @Column(name="time",nullable = false)
    private LocalDateTime time;
    @Column(name="seats_sold",nullable = false)
    private int soldSeats;


    public EventPlaceSeatsPrice getEventSeatPrice() {
        return eventSeatPrice;
    }

    public void setEventSeatPrice(EventPlaceSeatsPrice eventSeatPrice) {
        this.eventSeatPrice = eventSeatPrice;
    }

    public TicketsBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(TicketsBuyer buyer) {
        this.buyer = buyer;
    }

    public EventDistributor getDistributor() {
        return distributor;
    }

    public void setDistributor(EventDistributor distributor) {
        this.distributor = distributor;
    }
}
