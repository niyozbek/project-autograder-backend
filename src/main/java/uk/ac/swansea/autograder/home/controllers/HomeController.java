package uk.ac.swansea.autograder.home.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.swansea.autograder.home.services.HomeService;

@RestController
@RequestMapping("home")
public class HomeController {

    private final HomeService service;

    public HomeController(HomeService service) {
        this.service = service;
    }

    @GetMapping
    public @ResponseBody String index() {
        return service.getWelcomeMessage();
    }

}