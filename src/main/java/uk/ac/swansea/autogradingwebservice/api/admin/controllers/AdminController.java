package uk.ac.swansea.autogradingwebservice.api.admin.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Can create/edit lecturer profile and student profile.
 */
@RestController
@RequestMapping("api/admin")
public class AdminController {
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String index() {
        return "Welcome Admin!";
    }

    @GetMapping("student")
    @PreAuthorize("hasAuthority('STUDENT')")
    public String student() {
        return "U SHOULD NOT SEE THAT MESSAGE!";
    }
}
