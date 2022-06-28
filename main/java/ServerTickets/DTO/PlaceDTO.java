package ServerTickets.DTO;

import java.util.ArrayList;

public class PlaceDTO {
    private String name;
    private String description;
    private String address;
    private ArrayList<SeatsDTO> seats;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<SeatsDTO> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<SeatsDTO> seats) {
        this.seats = seats;
    }
}
