package uk.ac.swansea.autogradingwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AutograderWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutograderWebServiceApplication.class, args);
    }


}
