package com.example.petslist;

import com.example.petslist.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    LoginAttemptService attemptService;
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        attemptService.loginSucceeded(event.getAuthentication().getName());
    }
}
