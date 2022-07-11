package uk.ac.swansea.autogradingwebservice.api.student.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Submit the code, view results, view every test case of every submission
 */
@RestController
@RequestMapping("api/student")
public class StudentController {
    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public String index() {
        return "Welcome Student!";
    }
}
