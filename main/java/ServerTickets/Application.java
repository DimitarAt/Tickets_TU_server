package ServerTickets;


import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
    public class Application implements CommandLineRunner {


        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);

        }

    @Override
    public void run(String... param) {
        Configurator.initialize(null, "resources/log4j2.properties");
    }
    }


