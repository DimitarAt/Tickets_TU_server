package ServerTickets.DTO;

import java.time.LocalDateTime;
import java.util.HashMap;

public class SmallEventDTO {
    private String name;
    private String description;
    private LocalDateTime date;
    private String Type;
    private String organizerUsername;
    private HashMap<Long,Integer> seatPrices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getOrganizerUsername() {
        return organizerUsername;
    }

    public void setOrganizerUsername(String organizerUsername) {
        this.organizerUsername = organizerUsername;
    }

    public HashMap<Long, Integer> getSeatPrices() {
        return seatPrices;
    }

    public void setSeatPrices(HashMap<Long, Integer> seatPrices) {
        this.seatPrices = seatPrices;
    }
}

