package ServerTickets.Control;
import ServerTickets.DAO.CommunicationsDAOImpl;
import ServerTickets.DAO.CustomerDAOImpl;
import ServerTickets.DAO.CustomerDAO;
import ServerTickets.DAO.EventDAOImpl;
import ServerTickets.DTO.*;
import ServerTickets.entities.Customer;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TicketsController {
    @Autowired
    AuthenticationProvider provider;
    public static final Logger log = LogManager.getLogger(TicketsController.class);
    private int counter=0;
    @GetMapping(value="/tickets/customer" )
    public Customer CustomerById(HttpServletRequest request,@RequestParam(name="id", defaultValue = "102") long id) {
        long headerId=Long.parseLong(request.getHeader("id"));
        CustomerDAO customerDAO= new CustomerDAOImpl() ;
        log.info("Method CustomerById called");
        System.out.println(request.getSession().getId());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return customerDAO.GetCustomerById(headerId);}

    @GetMapping(value="/tickets/register/check_by_name" )
    public String CheckCustomerByName(HttpServletRequest request,HttpServletResponse response) {
        log.info("Method CheckCustomerByName called");
        String name=request.getHeader("name");
        log.info(name);
        CustomerDAOImpl customerDAO= new CustomerDAOImpl() ;
        return customerDAO.IsUserRegistered(name)?"TRUE":"";
       }


    @PostMapping(value= "/tickets/register/persist")

    public String PersistUser(  HttpServletRequest request
            , HttpServletResponse response) {
        String str=request.getParameter("jsonCustomer");
        Gson gson =new Gson();
        CustomerDTO customerDTO=gson.fromJson(str,CustomerDTO.class);
        Customer customer=customerDTO.GetCustomer();
        (new CustomerDAOImpl()).Insert(customer);
        log.info("Customer registered");
        return "New user registered";}

    @GetMapping(value="/tickets/admin/users",produces = "application/json")
    public ArrayList<CustomerAdminDTO> GetAllUsers(HttpServletRequest request, HttpServletResponse response){
        ArrayList<CustomerAdminDTO> customerList=(new CustomerDAOImpl().GetAllCustomers());
        return customerList;
    }
    @PostMapping(value ="/tickets/admin/manageRole")
    public String AddRole(HttpServletRequest request,HttpServletResponse response){
        log.info("method AddRole called");
        String customer=request.getHeader("customer");
        String role=request.getHeader("role");
        int result=(new CustomerDAOImpl()).AddRole(customer,role);
        return result+"Role added";
    }
    @GetMapping(value ="/tickets/admin/manageRole")
    public String RemoveRole(HttpServletRequest request,HttpServletResponse response){
        log.info("method RemoveRole called");
        String customer=request.getHeader("customer");
        String role=request.getHeader("role");
        int result=(new CustomerDAOImpl()).RemoveRole(customer,role);
        response.setHeader("result",String.valueOf(result));
        return result+"Role removed";
    }
    @DeleteMapping(value ="/tickets/admin/manageRole")
    public String RemoveCustomer(HttpServletRequest request,HttpServletResponse response){
        String customer=request.getHeader("customer");
        log.info("method RemoveCustomer called for customer: "+customer);
        int result=(new CustomerDAOImpl()).RemoveCustomer(customer);
        response.setHeader("result",String.valueOf(result));
        return result+"Customer removed";
    }
    @GetMapping(value ="/tickets/distributor/events")
    public String GetEvents(HttpServletRequest request
            , HttpServletResponse response){
        String distributor=SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> events=(new EventDAOImpl()).GetEventsByDistributor(distributor);
        return ConvertMapToSting(events);
    }


    @GetMapping(value ="/tickets/organizer/places")
    public String GetPlaces(HttpServletRequest request
            , HttpServletResponse response){
        Map<String,String> events=(new EventDAOImpl()).GetPlaces();
       String str=ConvertMapToSting(events);
        return str;
    }
    @GetMapping(value ="/tickets/organizer/events")
    public String GetEventsByUsername(HttpServletRequest request
            , HttpServletResponse response){
        String organizer=SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> events=(new EventDAOImpl()).GetAllEventsByOrganizer(organizer);
        return ConvertMapToSting(events);
    }

@GetMapping(value ="tickets/organizer/places/seats")
public ArrayList<SeatsDTO> GetSeats(HttpServletRequest request){
        long placeId=Long.parseLong(request.getHeader("placeId"));
    return (new EventDAOImpl()).GetSeatsByPlace(placeId);
}

    @GetMapping(value ="/tickets/organizer/event/id")
    public EventDTO GetSingleEventById(HttpServletRequest request){
        long eventId=Long.parseLong(request.getHeader("eventId"));
       Gson gson=new Gson();
       EventDTO e=(new EventDAOImpl()).GetEventById(eventId);
        return (new EventDAOImpl()).GetEventById(eventId);
    }

    @PostMapping(value = "/tickets/organizer/event/post")
    public String PersistEvent(HttpServletRequest request,HttpServletResponse response){
        log.info("method persist event called");
        String organizer=SecurityContextHolder.getContext().getAuthentication().getName();
        String jString=request.getParameter("event");
        Gson gson=new Gson();
        EventDTO eventDTO=gson.fromJson(jString, EventDTO.class);
        eventDTO.setOrganizerUsername(organizer);
        (new EventDAOImpl()).SetUpEvent(eventDTO);
        log.info("Event persisted: "+eventDTO.getName());
        return "Event added";
    }
    @GetMapping(value ="/tickets/customer/messages")
    public ArrayList<MessageDTO> GetMessages(HttpServletRequest request) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return(new CommunicationsDAOImpl()).CheckMessagesByRecipient(name);
    }
    @PostMapping(value ="/tickets/customer/messages")
    public String PersistMessage(HttpServletRequest request,HttpServletResponse response){
        log.info("method persist message called");
        String sender=SecurityContextHolder.getContext().getAuthentication().getName();
        String jString=request.getParameter("messageDTO");
        Gson gson=new Gson();
        MessageDTO messageDTO=gson.fromJson(jString, MessageDTO.class);
        messageDTO.setSender(sender);
        (new CommunicationsDAOImpl()).SendMessage(messageDTO);
        log.info("Message to: "+messageDTO.getRecipient());
        return "Message added";
    }


private String ConvertMapToSting (Map<String,String> map){

    return map.keySet().stream()
            .map(key -> key + "=" + map.get(key))
            .collect(Collectors.joining(","));
}

}