package uk.ac.swansea.autogradingwebservice.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.swansea.autogradingwebservice.auth.controllers.dto.LoginDto;
import uk.ac.swansea.autogradingwebservice.auth.services.AuthService;
import uk.ac.swansea.autogradingwebservice.exceptions.UnauthorizedException;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public String login(@Valid @RequestBody LoginDto loginDto) throws UnauthorizedException {
        return authService.login(loginDto);
    }
}
