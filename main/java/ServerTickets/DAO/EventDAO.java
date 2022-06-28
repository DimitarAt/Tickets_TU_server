package ServerTickets.DAO;

import ServerTickets.DTO.EventDTO;
import ServerTickets.DTO.PlaceDTO;
import ServerTickets.DTO.SeatsDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface EventDAO {
    public void CreatePlace(PlaceDTO place);
    public HashMap<String,String> GetPlaces(); //return place name and id
    public HashMap<String,String> GetSeatsByPlaceId(long placeId);
    public ArrayList<SeatsDTO> GetSeatsByPlace(long id);
    public void SetUpEvent(EventDTO eventDTO); //with place or not
    void SetPlaceForEvent(long eventId, HashMap<Long, Integer> seatsPrices);
    public Map<String,String> GetAllEventsByOrganizer(String username);
    public Map<String,String> GetEventsByDistributor(String username);
    public EventDTO GetEventById (long eventId);
    public void AddDistributorByEventIdAndUsername
            (Long eventId, String distributor, String organizer);
}
