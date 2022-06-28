package ServerTickets.entities;

import jakarta.persistence.*;

@Entity
@Table(name="event_distributor"
        ,uniqueConstraints = { @UniqueConstraint(name = "UniqueEventDistributor"
        , columnNames = { "event", "distributor" }) })
public class EventDistributor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="event",nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name="distributor", nullable = false)
    private Customer distributor;
    @Column (name="feedback_from_distributor")
    private String feedbackFromDistributor;
    @Column(name="feedback_from_organizer")
    private String feedbackFromOrganizer;
    @Column(name="feedback_rating_from_distributor")
    private int starsFromDistributor;
    @Column(name="feedback_rating_from_organizer")
    private int starsFromOrganizer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStarsFromDistributor() {
        return starsFromDistributor;
    }

    public void setStarsFromDistributor(int starsFromDistributor) {
        this.starsFromDistributor = starsFromDistributor;
    }

    public int getStarsFromOrganizer() {
        return starsFromOrganizer;
    }

    public void setStarsFromOrganizer(int starsFromOrganizer) {
        this.starsFromOrganizer = starsFromOrganizer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Customer getDistributor() {
        return distributor;
    }

    public void setDistributor(Customer distributor) {
        this.distributor = distributor;
    }

    public String getFeedbackFromDistributor() {
        return feedbackFromDistributor;
    }

    public void setFeedbackFromDistributor(String feedbackFromDistributor) {
        this.feedbackFromDistributor = feedbackFromDistributor;
    }

    public String getFeedbackFromOrganizer() {
        return feedbackFromOrganizer;
    }

    public void setFeedbackFromOrganizer(String feedbackFromOrganizer) {
        this.feedbackFromOrganizer = feedbackFromOrganizer;
    }
}
