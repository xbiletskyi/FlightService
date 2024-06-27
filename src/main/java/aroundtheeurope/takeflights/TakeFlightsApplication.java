package aroundtheeurope.takeflights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import aroundtheeurope.takeflights.SideFunctions.EnvPropertyLoader;

@SpringBootApplication
public class TakeFlightsApplication {
    static {
        EnvPropertyLoader.loadProperties();
    }
    public static void main(String[] args) {
        SpringApplication.run(TakeFlightsApplication.class, args);
    }
}
