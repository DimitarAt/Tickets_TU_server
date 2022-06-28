package ServerTickets.DTO;

import ServerTickets.entities.Customer;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.google.gson.Gson;
//import jakarta.persistence.Column;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;

public class CustomerDTO   {


    private String userName;
    private String firstName;

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public static Gson getGson() {
//        return gson;
//    }
//
//    public static void setGson(Gson gson) {
//        CustomerDTO.gson = gson;
//    }

    private String famName;
    private long phone;
    private String eMail;
    private String password;

    public CustomerDTO(String userName, String firstName, String famName, long phone, String eMail, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.famName = famName;
        this.phone = phone;
        this.eMail = eMail;
        this.password = password;
    }
    public Customer GetCustomer(){
        return new Customer(userName,firstName,famName,phone,eMail,password,"USER");
    }



//    private static Gson gson=new Gson();
//    public static Customer GetCustomer(String customerJson){
//    return gson.fromJson(customerJson,Customer.class);}
//
//    public static String getJson (Customer customer)  {
//        return gson.toJson(customer);
//
//    }








}
