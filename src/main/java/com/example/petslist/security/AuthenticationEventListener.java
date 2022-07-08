package com.example.petslist.security;

import com.example.petslist.model.User;
import com.example.petslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthenticationEventListener {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginAttemptsService loginAttemptsService;

    @EventListener
    public void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        User user = userService.findByUsername(event.getAuthentication().getName());
        loginAttemptsService.loginSucceeded(user);
    }

    @EventListener
    @Transactional
    public void onAuthenticationFailureBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent event) {
        User user = userService.findByUsername(event.getAuthentication().getName());
        if (user != null) {
            loginAttemptsService.loginFailed(user);
            if (loginAttemptsService.isUserMaxAttemptsReached(user)) {
                userService.lock(user);
            }
        }
    }
}
