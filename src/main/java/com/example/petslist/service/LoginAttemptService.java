package com.example.petslist.service;

import com.example.petslist.repository.AttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPT = 10;

    public void loginFailed(String username) {
    }

    public void loginSucceeded(String username) {
    }
//    private LoadingCache<String, Integer> attemptsCache;
//
//    @Autowired
//    AttemptsRepository attemptsRepository;
//
//    public LoginAttemptService() {
//        super();
//        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
//            @Override
//            public Integer load(final String key) {
//                return 0;
//            }
//        });
//    }
//
//    //
//
//    public void loginSucceeded(final String key) {
//        attemptsCache.invalidate(key);
//    }
//
//    public void loginFailed(final String username) {
//        int attempts = 0;
//        try {
//            attempts = attemptsRepository.get(username);
//        } catch (final ExecutionException e) {
//            attempts = 0;
//        }
//        attempts++;
//        attemptsCache.put(key, attempts);
//    }
//
//    public boolean isBlocked(final String username) {
//        try {
//            return attemptsCache.get(username) >= MAX_ATTEMPT;
//        } catch (final ExecutionException e) {
//            return false;
//        }
//    }
}
