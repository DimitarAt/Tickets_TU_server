package ServerTickets.DAO;

import ServerTickets.DTO.CustomerAdminDTO;
import ServerTickets.entities.Customer;
import ServerTickets.initialization.HibernateManager;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.*;

@Component
public class CustomerDAOImpl implements CustomerDAO, UserDetailsService {
HibernateManager hibernateManager=new HibernateManager();
    public static final Logger log = LogManager.getLogger(CustomerDAOImpl.class);

    public void Insert(Customer customer){
        if (!IsUserRegistered(customer.getName())){             //check if user is already registered
        EntityManager em=hibernateManager.GetEntityManager();
        em.getTransaction().begin();
        em.persist(customer);                                    //persist user
//        em.persist(new CustomerRoles(customer,Roles.USER));      //persist default role: user
        em.getTransaction().commit();
        em.close();
        log.info("new customer with role: user persisted");}
        else{log.info("there is already such customer");}
    }
    public ArrayList<CustomerAdminDTO> GetAllCustomers(){
        String query="SELECT new ServerTickets.DTO.CustomerAdminDTO(c.name ,CONCAT(c.firstName,\" \",c.famName),c.authorities)" +
                " FROM Customer c";
        EntityManager em=hibernateManager.GetEntityManager();
        ArrayList<CustomerAdminDTO> customerList =(ArrayList<CustomerAdminDTO>) em.createQuery(query).getResultList();
        em.close();
        return customerList;
    }

    public  int AddRole (String name,String role){
        EntityManager em=hibernateManager.GetEntityManager();
        String query="UPDATE Customer c SET " +
                "c.authorities= " +
                "CONCAT(c.authorities,:role) WHERE" +
                " c.name=:username";
        em.getTransaction().begin();
        int result=em.createQuery(query).setParameter("username",name)
                        .setParameter("role",","+role).executeUpdate();
        em.close();
        log.info("Role added");
        return result;
    }
    public int RemoveRole(String customer,String role){
        EntityManager em=hibernateManager.GetEntityManager();
        String query="UPDATE Customer c SET " +
                "c.authorities= " +
                "REPLACE(c.authorities,:role,\"\") WHERE" +
                " c.name=:username";
        em.getTransaction().begin();
        int result=em.createQuery(query).setParameter("username",customer)
                .setParameter("role",","+role).executeUpdate();
        em.close();
        log.info("Role removed");
        return result;
    }
   public int RemoveCustomer(String customer){
       EntityManager em=hibernateManager.GetEntityManager();
       String query="DELETE FROM Customer c WHERE" +
               " c.name=:username";
       em.getTransaction().begin();
       int result=em.createQuery(query).setParameter("username",customer).executeUpdate();
       em.close();
       log.info("Customer removed");
       return result;
   }

    public  Customer GetCustomerById (long id){
        String query="SELECT c FROM Customer c WHERE c.id= :id";
        EntityManager em=hibernateManager.GetEntityManager();
        Customer customer =(Customer) em.createQuery(query)
                .setParameter("id",id).getSingleResult();
        em.close();

        return customer;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String query="SELECT c FROM Customer c WHERE c.name= :name";
        EntityManager entityManager=hibernateManager.GetEntityManager();
        Customer customer =(Customer) entityManager
                .createQuery(query)
                .setParameter("name",username)
                .getSingleResult();
        entityManager.close();
        log.info("User Details retrieved");

        return new User(customer.getName()
                , customer.getPassword()
                ,AuthorityUtils.commaSeparatedStringToAuthorityList(customer.getAuthorities()));



//        String retrieveRoles="SELECT r.role FROM CustomerRoles r WHERE r.customer.id= :customerId";
//        List enumRoles=  entityManager.createQuery(retrieveRoles)
//                        .setParameter("customerId",customer.getId())
//                .getResultList();
//        Collection <GrantedAuthority> grantedList=new ArrayList<GrantedAuthority>();
//        for (Object role:enumRoles
//             ) {
//            grantedList.add(new Role(role.toString()));
//            System.out.println(role.toString());
//        }
//
//
//
    }



//    public List LoadCustomersByRole (Roles role){
//        String getCustomersByRole="SELECT c FROM Customer c INNER JOIN CustomerRoles r on " +
//                "c.id=r.customer.id WHERE r.role=:role";
//        EntityManager entityManager= hibernateManager.GetEntityManager();
//        entityManager.getTransaction().begin();
//        List customers= entityManager.createQuery(getCustomersByRole)
//                .setParameter("role", role).getResultList();
//        entityManager.close();
//        log.info("users withe specific role retrieved");
//        return customers;
//    }

public Boolean IsUserRegistered (String username){

    String query="SELECT COUNT (c) FROM Customer c WHERE c.name= :name";
    System.out.println(username);
    EntityManager entityManager=hibernateManager.GetEntityManager();
    entityManager.getTransaction().begin();
    long persistedCustomer =(long) entityManager
            .createQuery(query)
            .setParameter("name",username)
            .getSingleResult();
    entityManager.close();
    System.out.println(persistedCustomer);
    if (persistedCustomer==0){log.info("no such user registered yet");return false;}
    else {log.info("user is already registered");return  true;}


}
public void UpdateToken (String username, String token){
    String query="UPDATE Customer c SET c.token= :token WHERE c.name= :username ";
    EntityManager entityManager=hibernateManager.GetEntityManager();
    entityManager.getTransaction().begin();
    entityManager
            .createQuery(query)
            .setParameter("token",token)
            .setParameter("username",username)
            .executeUpdate();
    entityManager.close();
    log.info("jwt token persisted");

}

    @Override
    public Customer FindCustomerByName(String name) {
        String queryFindByName="SELECT c FROM Customer c WHERE c.name = :userName";
        EntityManager entityManager=hibernateManager.GetEntityManager();
        Customer customer=(Customer) entityManager
                .createQuery(queryFindByName)
                .setParameter("userName",name)
                .getSingleResult();
        entityManager.close();
        return customer;
    }


//    public String AuthToString(Collection<? extends GrantedAuthority> list){
//        String rolesAsString="";
//       Object[] arrayOfRoles= list.toArray();
//        for (int i=0;i<arrayOfRoles.length;i++)
//        {
//            rolesAsString+=((Role)arrayOfRoles[i]).getName()+",";
//        }
//        return  rolesAsString;
//    }
//    public Collection<? extends GrantedAuthority> StringToAuth (String roles){
//
//         String[] arrayOfStrings=roles.split("[,]+");
//         ArrayList<Role> listOfRoles=new ArrayList<>();
//         for (int i=0; i<arrayOfStrings.length;i++){
//             listOfRoles.add(new Role(arrayOfStrings[i]));
//         }
//        Collection<? extends GrantedAuthority> collectionOfRoles=listOfRoles;
//        return  collectionOfRoles;
//    }

}
