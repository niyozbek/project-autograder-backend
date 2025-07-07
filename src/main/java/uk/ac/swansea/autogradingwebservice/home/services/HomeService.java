package uk.ac.swansea.autogradingwebservice.home.services;

import org.springframework.stereotype.Service;

@Service
public class HomeService {
    public String getWelcomeMessage() {
        return "Welcome to Autograder Web Service";
    }
}
