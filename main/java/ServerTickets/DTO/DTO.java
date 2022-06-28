package ServerTickets.DTO;

import ServerTickets.entities.Customer;
import com.google.gson.Gson;

public abstract class DTO {
   private static Gson gson=new Gson();
    public static String getJson(Object obj){

        return gson.toJson(obj);
    }
    public static Object getObject(String jsonString){
        return gson.fromJson(jsonString, Customer.class);
    }

}
