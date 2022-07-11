package uk.ac.swansea.autogradingwebservice.api.lecturer.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create task, so that students can submit a code for it
 * Can view grade of each student in terms of valid test cases and percentage
 * Can view all tasks created previously
 * can't view tasks created by other lecturers
 */
@RestController
@RequestMapping("api/lecturer/about")
public class LecturerController {
    @GetMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public String index() {
        return "Welcome Lecturer!";
    }
}
