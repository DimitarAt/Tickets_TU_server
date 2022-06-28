package ServerTickets.DAO;

import ServerTickets.DTO.EventDTO;
import ServerTickets.DTO.PlaceDTO;
import ServerTickets.DTO.SeatsDTO;
import ServerTickets.entities.*;
import ServerTickets.initialization.HibernateManager;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventDAOImpl implements EventDAO{
        HibernateManager hibernateManager=new HibernateManager();
        public static final Logger log = LogManager.getLogger(EventDAOImpl.class);

    @Override
    public void CreatePlace(PlaceDTO placeDTO) {
        Place place = new Place(placeDTO.getName()
                ,placeDTO.getAddress(),placeDTO.getDescription());
        PlaceSeats placeSeats;//=new PlaceSeats();

        EntityManager entityManager=hibernateManager.GetEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(place);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();

        for (SeatsDTO seatsDTO:placeDTO.getSeats()
        ) {placeSeats=new PlaceSeats();
            placeSeats.setPlace(place);
           placeSeats.setSeatsType(seatsDTO.getType());
           placeSeats.setSeatsDescription(seatsDTO.getDescription());
           placeSeats.setNumber(seatsDTO.getNumber());
           entityManager.persist(placeSeats);

        }entityManager.getTransaction().commit();
        entityManager.close();
        log.info("New place persisted.");
    }

    @Override
    public HashMap<String , String> GetPlaces() {
        String getPlacesQuery="SELECT  p.id as id, p.name as name FROM Place p";
        EntityManager entityManager=hibernateManager.GetEntityManager();
        HashMap<String ,String > resultList=
                (HashMap<String, String>) entityManager.createQuery(getPlacesQuery, Tuple.class)
                        .getResultStream()
                        .collect(Collectors.toMap(tuple->((String) tuple.get("id").toString())
                                ,tuple -> ((String) tuple.get("name")))
                        );
        entityManager.close();
        log.info("GetPlaces called");
         return resultList;
    }

    @Override
    public HashMap<String, String> GetSeatsByPlaceId(long placeId) {
            String getSeatsQuery="SELECT  ps.id as id, ps.seatsDescription as type FROM PlaceSeats ps " +
                    "INNER JOIN Place p ON ps.place.id=p.id WHERE p.id = :place_id";
            EntityManager entityManager=hibernateManager.GetEntityManager();
            HashMap<String ,String > resultList=
                    (HashMap<String, String>) entityManager
                            .createQuery(getSeatsQuery, Tuple.class).
                            setParameter("place_id",placeId)
                            .getResultStream()
                            .collect(Collectors.toMap(tuple->((String) tuple.get("id").toString())
                                    ,tuple -> ((String) tuple.get("type")))
                            );
            entityManager.close();
            log.info("GetSeatsByPlaceId called");
            return resultList;
    }
    @Override
    public ArrayList<SeatsDTO> GetSeatsByPlace(long id) {
        String getSeatsQuery="SELECT new ServerTickets.DTO.SeatsDTO( ps.id , ps.seatsDescription," +
                "ps.seatsType, ps.number)FROM PlaceSeats ps  " +
                "INNER JOIN Place p ON ps.place.id=p.id WHERE p.id = :place_id";
        EntityManager entityManager=hibernateManager.GetEntityManager();
      List seats= entityManager
                        .createQuery(getSeatsQuery)
                        .setParameter("place_id",id)
                        .getResultList();
        entityManager.close();
        log.info("GetSeatsByPlaceId called");
        return  (ArrayList<SeatsDTO>) seats;
    }

    @Override
    public void SetUpEvent(EventDTO eventDTO) {
        Event event=new Event();

        try {
        event=CreateEvent(eventDTO);
    }catch (Exception e){System.out.println("dshgjfghjgfjhgfj");}

        if(!eventDTO.getSeatPrices().isEmpty()||eventDTO.getSeatPrices()!=null)
        {
            SetUpEventPrices(event,eventDTO.getSeatPrices());
        }
        log.info("Event set up");
    }
    private Event CreateEvent(EventDTO eventDTO) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String getOrganizerQuery="SELECT c FROM Customer c WHERE c.name= :userName";//to check if there is
        Event event=new Event();                            ////another event on that date
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setType(eventDTO.getType());


            event.setDate(format.parse(eventDTO.getDate()));


        EntityManager entityManager=hibernateManager.GetEntityManager();
        entityManager.getTransaction().begin();
        event.setOrganizer((Customer) entityManager.createQuery(getOrganizerQuery)
                .setParameter("userName",eventDTO.getOrganizerUsername())
                .getSingleResult());
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        entityManager.close();
        log.info("Event created");
        return event;
    }
    private void SetUpEventPrices(Event event,HashMap<Long,Integer> seatsPrices){
        String getPlaceSeatsQuery="SELECT ps FROM PlaceSeats ps WHERE ps.id= :placeSeatsId";

        EventPlaceSeatsPrice eventSeats;
        EntityManager entityManager=hibernateManager.GetEntityManager();
        entityManager.getTransaction().begin();

            for (long seat:seatsPrices.keySet()
            ) {
                eventSeats=new EventPlaceSeatsPrice();
                eventSeats.setEvent(event);
                eventSeats.setPlaceSeats((PlaceSeats) entityManager
                        .createQuery(getPlaceSeatsQuery)
                        .setParameter("placeSeatsId",seat).getSingleResult());
                eventSeats.setPrice(seatsPrices.get(seat));
                entityManager.persist(eventSeats);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            log.info("Prices for event set up");
    }

    @Override
    public void SetPlaceForEvent(long eventId, HashMap<Long, Integer> seatsPrices) {
        Event event;
        EntityManager entityManager=hibernateManager.GetEntityManager();
        event=entityManager.find(Event.class,eventId);
       SetUpEventPrices(event,seatsPrices);
        log.info("Prices for event set up");
        entityManager.close();
    }

    @Override
    public Map<String, String> GetAllEventsByOrganizer(String username) {
        EntityManager entityManager=hibernateManager.GetEntityManager();
        String GetEventsByOrganizer="SELECT e.id as id,e.name as name FROM Event e INNER JOIN " +
                "Customer c ON e.organizer.id=c.id WHERE c.name= :userName";
        HashMap<String,String> map=(HashMap<String, String>) entityManager
                .createQuery(GetEventsByOrganizer,Tuple.class)
                .setParameter("userName",username)
                .getResultStream()
                .collect(Collectors.toMap(
                        tuple -> ((String) tuple.get("id").toString()),
                        tuple->((String) tuple.get("name"))
                ));
        entityManager.close();
        log.info("Method GetAllEventsByOrganizer called.");
        return  map;
    }
////checks for timing to be added
    @Override
    public Map<String, String> GetEventsByDistributor(String username) {
        EntityManager entityManager=hibernateManager.GetEntityManager();
        String GetEventsByDistributor="SELECT  e.id as id,e.name as name FROM Event e INNER JOIN " +
                "EventDistributor ed ON e.id=ed.event.id " +
                "INNER JOIN Customer c ON ed.distributor.id=c.id " +
                "WHERE c.name= :userName";
        HashMap<String,String> map=(HashMap<String, String>) entityManager
                .createQuery(GetEventsByDistributor,Tuple.class)
                .setParameter("userName",username)
                .getResultStream()
                .collect(Collectors.toMap(
                        tuple -> ((String) tuple.get("id").toString()),
                        tuple->((String) tuple.get("name"))
                ));
        entityManager.close();
        log.info("Method GetEventsByDistributor called.");
        return  map;
    }

    @Override
    public EventDTO GetEventById(long eventId) {
        String queryEvent="SELECT e FROM Event WHERE e.id= :eventId";
        String queryEventSeats="SELECT es FROM EventPlaceSeatsPrice es WHERE es.event.id= :eventId";
        String placeQuery="SELECT ps.place.id FROM EventPlaceSeatsPrice es INNER JOIN PlaceSeats ps ON " +
                "es.placeSeats.id=ps.id WHERE es.event.id= :eventId" ;
        EventDTO eventDTO=new EventDTO();
        EntityManager entityManager=hibernateManager.GetEntityManager();
//      Event event= entityManager.createQuery(queryEvent)
//                .setParameter("eventId",eventId)
//                .getSingleResult();
        Event event=entityManager.find(Event.class,eventId);

        List eventSeats=entityManager.createQuery(queryEventSeats)
                .setParameter("eventId",eventId)
                .getResultList();

        List eventPlace=entityManager.createQuery(placeQuery)
                .setParameter("eventId",eventId)
                .setMaxResults(1)
                .getResultList();
        entityManager.close();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


//        List list=entityManager.createQuery(placeQuery)
//                .setParameter("eventId",(long)502)
//                .setMaxResults(1)
//                .getResultList();
//        System.out.println(list.get(0).toString());


        eventDTO.setName(event.getName());
        eventDTO.setDate(format.format(event.getDate()));
        eventDTO.setType(event.getType());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setOrganizerUsername(event.getOrganizer().getName());
        eventDTO.setPlaceId((long) eventPlace.get(0));
        eventDTO.setSeatPrices(EventSeatsListToDTOMap(eventSeats));
        EventDTO e=eventDTO;
        return eventDTO;
    }



    @Override
    public void AddDistributorByEventIdAndUsername
            (Long eventId,String organizer, String distributor) {
        EventDistributor eventDistributor=new EventDistributor();
        EntityManager entityManager=hibernateManager.GetEntityManager();
        if (GetAllEventsByOrganizer(organizer).containsKey(eventId.toString())){
            eventDistributor.setDistributor((new CustomerDAOImpl()).FindCustomerByName(distributor));
            eventDistributor.setEvent(entityManager.find(Event.class,eventId));
            entityManager.getTransaction().begin();
            entityManager.persist(eventDistributor);
            entityManager.getTransaction().commit();
            entityManager.close();
            log.info("Distributor added to event");
        }else {log.info("Operation denied! Not the organizer of the event");}

    }

    private HashMap<Long ,Integer> EventSeatsListToDTOMap(List list){
        HashMap<Long ,Integer> map=new HashMap<>();
        for (Object obj:list
             ) {
            map.put(((EventPlaceSeatsPrice)obj).getId()
                    ,((EventPlaceSeatsPrice)obj).getPrice());
        }
        return map;
    }

}
