package com.example.petslist.controller;

import com.example.petslist.model.User;
import com.example.petslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RegistrationController {

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    User registration(@RequestBody User newUser, HttpServletRequest request) {

        String username = newUser.getUsername();
        String password = newUser.getPassword();

        User user = userService.register(newUser);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return user;
    }

    @GetMapping("/register/checkname/{username}")
    ResponseEntity<?> checkUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "username", username,
                "available", Boolean.toString(!userService.usernameExists(username))
                )
        );
    }
}
