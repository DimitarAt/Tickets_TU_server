package ServerTickets.DAO;

import ServerTickets.DTO.FeedbackDTO;
import ServerTickets.DTO.MessageDTO;

import java.util.ArrayList;

public interface CommunicationDAO {
    public void SendMessage(MessageDTO messageDTO);
    public void GiveFeedback(FeedbackDTO feedbackDTO);
    public ArrayList<MessageDTO> CheckMessagesByRecipient(String recipient);
    public String UnreceivedMessages(String username);
}
