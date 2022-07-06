package com.example.petslist;

import com.example.petslist.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    LoginAttemptService attemptService;
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        attemptService.loginFailed(event.getAuthentication().getName());
    }
}
