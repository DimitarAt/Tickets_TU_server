package ServerTickets;

import ServerTickets.DAO.*;
import ServerTickets.initialization.HibernateManager;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

//SecurityConfiguration security=new SecurityConfiguration();
//    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
//    Object obj=authentication.getPrincipal();
//    String name= ((UserDetails) obj).getUsername();

    public static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main m = new Main();
        HibernateManager hibernateManager=new HibernateManager();

EntityManager em=hibernateManager.GetEntityManager();
/////////////////////////////////////customer////////////////////////////////////////////////
//        CustomerDAOImpl c=new CustomerDAOImpl();
//        for (int i=0;i<20;i++) {
//            Customer customer = new Customer();
//            customer.setName("user"+i);
//            customer.seteMail("email"+i+"gmail.com");
//            customer.setPhone(i+1010101);
//            customer.setPassword("user"+i);
//            customer.setAuthorities("USER,ADMIN");
//            customer.setFirstName("name"+i);
//            customer.setFamName("family"+i);
//            c.Insert(customer);}


///////////////////////////////////////
        EventDAO e=new EventDAOImpl();/////////place seats//////////////////////////////////////////
//
//        SeatsDTO seatsDTO;//=new SeatsDTO();
//        ArrayList <SeatsDTO> list;
//        PlaceDTO placeDTO=new PlaceDTO();
//
//        for (int i=0;i<50;i++) {
//            list = new ArrayList<>();
//            for (int k = 0; k < 5; k++) {
//                seatsDTO=new SeatsDTO();
//                seatsDTO.setDescription("Seats in sector: C"+i );
//                seatsDTO.setType("VIP arm chairs"+i);
//                seatsDTO.setNumber((k + 1) * 100);
//                list.add(seatsDTO);
//            }
//            placeDTO.setName("Place name: concert hall" + i);
//            placeDTO.setAddress("Place address Варна" + i);
//            placeDTO.setDescription("Very big and nice hall" + i);
//            placeDTO.setSeats(list);
//            e.CreatePlace(placeDTO);
//        }
//////////////////////////////////getPlaces///////////////////////



//        HashMap<String , String> map=e.GetPlaces();
//        for (String key: map.keySet()
//             ) {
//            System.out.println(key+"  "+map.get(key));
//        }map.keySet();

//////////////////////////////get seats by place ID///////////////////////////////////////


//        HashMap<String , String> map=e.GetSeatsByPlaceId(705);
//        for (String key: map.keySet()
//             ) {
//            System.out.println(key+"  "+map.get(key));
//        }map.keySet();




//        ArrayList<SeatsDTO> map=e.GetSeatsByPlace(705);
//        for (SeatsDTO key: map
//             ) {
//            System.out.println(key.getDescription() + "  " + key.getId());
//        }







//////////////////////////////////////set up Event/////////////////
//        EventDTO eventDTO=new EventDTO();
//        HashMap<Long,Integer> map=new HashMap<>();
//        map.put((long) 462,67);
//        map.put((long) 463,50);
//        map.put((long) 464,37);
//        map.put((long) 465,100);
//     ////   e.SetPlaceForEvent(252,map);
//        eventDTO.setDescription("description112");
//        eventDTO.setName("name112");
//        eventDTO.setType("type112");
//        eventDTO.setDate(LocalDateTime.now());
//        eventDTO.setOrganizerUsername("user1");
//        eventDTO.setSeatPrices(map);
//        e.SetUpEvent(eventDTO);
//////////////////////////get events by username//////////////

//        Map<String , String> map1=e.GetAllEventsByOrganizer("user2");
//        for (String key: map1.keySet()
//             ) {
//            System.out.println(key+"  "+map1.get(key));
//        }

////////////////////////////Add distributor//////////////////////////

//e.AddDistributorByEventIdAndUsername((long)352,"user2","user1");

/////////////////////////////////GetEventsByDistributor///////////////
//
//                Map<String , String> map2=e.GetEventsByDistributor("user1");
//        for (String key: map2.keySet()
//             ) {
//            System.out.println(key+"  "+map2.get(key));
//        }

/////////////////////////////GetEventsByOrganizer
//                Map<String , String> map2=e.GetAllEventsByOrganizer("user1");
//        for (String key: map2.keySet()
//             ) {
//            System.out.println(key+"  "+map2.get(key));
//        }

////////////////////////////GetEventDTO by id

//System.out.println(e.GetEventById((long) 502).getName());

////////////////////////////////////////
//        String placeQuery="SELECT ps.place.id FROM EventPlaceSeatsPrice es INNER JOIN PlaceSeats ps ON " +
//                "es.placeSeats.id=ps.id WHERE es.event.id= :eventId" ;
//
//       List list=em.createQuery(placeQuery)
//                .setParameter("eventId",(long)502)
//                .setMaxResults(1)
//                .getResultList();
//        System.out.println(list.get(0).toString());

////////////////////////////message persist
//        CommunicationDAO communicationDAO=new CommunicationsDAOImpl();
////
//
//        for (int i=0;i<20;i++) {
//           MessageDTO messages1=new MessageDTO();
//              messages1.setMessage("Hello message. Welcome, new user"+i);
//              messages1.setSender("user"+i);
//              messages1.setRecipient("user2");
//             communicationDAO.SendMessage(messages1);
//        }

        /////////////////////////////check messages

//
//        ArrayList<MessageDTO> map=communicationDAO.CheckMessagesByRecipient("user2");
//        for (MessageDTO key: map
//             ) {
//            System.out.println(key.getMessage());
//        }
/////////////////////////////////feedback giving
//        FeedbackDTO feedbackDTO=new FeedbackDTO();
//        feedbackDTO.setFeedback("Excellent");
//        feedbackDTO.setStars(10);
//        feedbackDTO.setEventId(402);
//        feedbackDTO.setSender("user1");
//        feedbackDTO.setRecipient("user2");
//        communicationDAO.GiveFeedback(feedbackDTO);
//

///////////////////////new Messages



        String query="SELECT COUNT (m) FROM Messages m INNER JOIN " +
                "Customer c on c.id= m.recipient.id WHERE m.received =:received " +
                "AND c.name= :username";

        Object count= em.createQuery(query)
                .setParameter("received",false)
                .setParameter("username","user2")
                .getSingleResult();
        System.out.println(count.toString()+"dsghgfnhdgnd");

//        EntityManager em = new HibernateManager().GetEntityManager();
//        String getSeatsQuery="SELECT  ps.id as id, ps.seatsType as type FROM PlaceSeats ps " +
//                "INNER JOIN Place p ON ps.place.id=p.id WHERE p.id = :placeId";
//
//        em.getTransaction().begin();
//        List list=em.createQuery(getSeatsQuery)
//                .setParameter(("placeId"),705).getResultList();





//m.security.userDetailsService().loadUserByUsername("Dragan");
//        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
//        Object obj=authentication.getPrincipal();
//        String name= ((UserDetails) obj).getUsername();


//   Appender appender= FileAppender.newBuilder()

//        EntityManager em = new HibernateManager().GetEntityManager();
//        CustomerDAOImpl customerDAO=new CustomerDAOImpl();
//        customerDAO.RemoveRole("mitko","ORGANIZER");

//        ArrayList<CustomerAdminDTO> list=customerDAO.GetAllCustomers();
//        for (CustomerAdminDTO cust:list
//             ) {
//            System.out.println(cust.getUsername());
//        }

//        System.out.println(customerDAO.AddRole("mitko", Roles.ORGANIZER));
//       List set=customerDAO.LoadCustomersByRole(Roles.USER);


//        for (Object customer: set
//             ) {
//            System.out.println(((Customer) customer).getName());
//        }














em.close();

}}

    //        CustomerDTO customerDTO = new CustomerDTO(customer);
//    UserDetails user = customerDAO.loadUserByUsername("mitko");
//    System.out.println(user.getAuthorities());





//        System.out.println(json);
//        System.out.println(customerDTO.getCustomer(json).getName());

//        HibernateManager hibernateManager=new HibernateManager();
//        String query="SELECT c FROM Customer c WHERE c.id=102";
//        EntityManager em=hibernateManager.GetEntityManager();
//        Customer customer =(Customer) em.createQuery(query).getSingleResult();
//        String jsonCustomer=CustomerDTO.getJson(customer);
//        System.out.println(jsonCustomer);
//        System.out.println(CustomerDTO.GetCustomer(jsonCustomer).getName());
//      log.info("customer by id retrieved");






