package uk.ac.swansea.autograder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AutograderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutograderApplication.class, args);
    }


}
