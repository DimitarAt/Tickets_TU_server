package ServerTickets.DAO;

import ServerTickets.DTO.FeedbackDTO;
import ServerTickets.DTO.MessageDTO;
import ServerTickets.entities.Messages;
import ServerTickets.initialization.HibernateManager;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommunicationsDAOImpl implements CommunicationDAO{
    HibernateManager hibernateManager=new HibernateManager();
    public static final Logger log = LogManager.getLogger(CommunicationsDAOImpl.class);
  //  static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");

    @Override
    public void SendMessage(MessageDTO messageDTO) {
        Messages message=new Messages();
        CustomerDAO customerDAO=new CustomerDAOImpl();
        MessageDTO m=messageDTO;
        message.setSender(customerDAO.FindCustomerByName(messageDTO.getSender()));
        message.setRecipient(customerDAO.FindCustomerByName(messageDTO.getRecipient()));
        message.setSendDate( LocalDateTime.now());
        message.setContent(messageDTO.getMessage());
        message.setReceived(false);
        message.setReceiveDate(null);
        EntityManager entityManager=hibernateManager.GetEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(message);
        entityManager.getTransaction().commit();
        entityManager.close();
        log.info("message persisted");
    }

    @Override
    public void GiveFeedback(FeedbackDTO feedbackDto) {
       CustomerDAOImpl customerDAO=new CustomerDAOImpl();
        long recipient=customerDAO.FindCustomerByName(feedbackDto.getRecipient()).getId();
        long sender=customerDAO.FindCustomerByName(feedbackDto.getSender()).getId();
       EntityManager entityManager=hibernateManager.GetEntityManager();
    String query="UPDATE EventDistributor ed SET ed.feedbackFromDistributor= " +
            "CASE ed.distributor.id   WHEN :sender THEN  :feedback, " +
            " starsFromDistributor  = CASE WHEN distributor.id= :sender " +
            "THEN :stars " ;

    String querySelect="SELECT ed.id FROM EventDistributor ed WHERE (ed.distributor.id=: sender OR " +
            "ed.distributor.id= :recipient) AND ed.event.id= :eventId";
    System.out.println(recipient+"   "+sender+"    "+feedbackDto.getEventId());
    entityManager.getTransaction().begin();
            entityManager.createQuery(query)
                    .setParameter("sender",sender)
                   // .setParameter("recipient",recipient)
                 //   .setParameter("event",feedbackDto.getEventId())
                    .setParameter("feedback",feedbackDto.getFeedback())
                    .setParameter("stars",feedbackDto.getStars())
                    .executeUpdate();
      long result=(long)  entityManager.createQuery(querySelect)
                .setParameter("sender",sender)
                    .setParameter("recipient",recipient)
                    .setParameter("eventId",feedbackDto.getEventId())
                            .getSingleResult();


        System.out.println(result+"");

            entityManager.close();
            log.info("feedback given");

    }

    @Override
    public ArrayList<MessageDTO> CheckMessagesByRecipient(String recipient) {
        String checkMessagesQuery="SELECT m FROM Messages m WHERE m.recipient.name= :recipient";
        String seenMessagesUpdate="UPDATE Messages   SET received = :received, " +
                " receiveDate= :receiveDate  WHERE recipient.id= :recipient";
        ArrayList<MessageDTO> list=new ArrayList<>();



        EntityManager entityManager=hibernateManager.GetEntityManager();
        long recipientId=((new CustomerDAOImpl()).FindCustomerByName(recipient)).getId();
        List messagesList=entityManager.createQuery(checkMessagesQuery)
                .setParameter("recipient",recipient)
                .getResultList();
        for (Object obj:messagesList
             ) {
            Messages messages=(Messages) obj;
            MessageDTO messageDTO=new MessageDTO();
            messageDTO.setMessage("("+messages.getSendDate().format(formatter)
                    +")  from: "+messages.getSender().getName().toUpperCase()
                    +" / "+"opened: "+messages.isReceived()+" / "+messages.getContent());
           messageDTO.setSender(messages.getSender().getName());
           list.add(messageDTO);
        }

        entityManager.getTransaction().begin();
        entityManager.createQuery(seenMessagesUpdate)   ///////////set received
               .setParameter("received",true)
                .setParameter("receiveDate",LocalDateTime.now())
             .setParameter("recipient",recipientId)
                .executeUpdate();

        entityManager.close();
        log.info("messages retrieved: "+list.size());
        return list;
    }
    public String UnreceivedMessages(String username){
        String query="SELECT COUNT (m) FROM Messages m INNER JOIN " +
                "Customer c on c.id= m.recipient.id WHERE m.received =:received " +
                "AND c.name= :username";
        EntityManager entityManager=hibernateManager.GetEntityManager();
        Object count=entityManager.createQuery(query)
                .setParameter("received",false)
                .setParameter("username",username).getSingleResult();
        return count.toString();
    }



}
