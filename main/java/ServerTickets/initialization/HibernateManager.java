package ServerTickets.initialization;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//import javax.persistence.EntityManager;


@Component
public class HibernateManager {
//    private static EntityManagerFactory emFactory = HibernateManager.BuildFactory();
//    private static final String confFile ="hibernate.cfg.xml";
//
//    public static EntityManagerFactory BuildFactory() {
//        Configuration configuration = new Configuration().configure(confFile);
//       EntityManagerFactory factory = configuration.buildSessionFactory();
//        return factory;
//    }
//
//    public static EntityManager GetEntityManager() {
//        return  emFactory.createEntityManager();
//    }
//
//    public static void FactoryClose() {
//        emFactory.close();
//    }
    private  EntityManagerFactory emFactory;
    private  final String confFile = "hibernate.cfg.xml";

    public HibernateManager(){
        Configuration configuration = new Configuration().configure(confFile);
        emFactory= configuration.buildSessionFactory();
    }
    public  EntityManager GetEntityManager() {
        return  emFactory.createEntityManager();
    }

    public  void FactoryClose() {
        emFactory.close();}
}