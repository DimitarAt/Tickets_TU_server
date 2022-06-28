package ServerTickets.DAO;

import ServerTickets.DTO.CustomerAdminDTO;
import ServerTickets.entities.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface CustomerDAO {
    public  void Insert (Customer customer);
    public  Customer GetCustomerById (long id);
    public ArrayList<CustomerAdminDTO> GetAllCustomers();
    public  int AddRole (String name,String role);
    public int RemoveRole(String customer,String role);
    public int RemoveCustomer(String customer);
    public UserDetails loadUserByUsername(String username);
    public Boolean IsUserRegistered (String username);
    public void UpdateToken (String username, String token);
    public Customer FindCustomerByName(String username);





}
