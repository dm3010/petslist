package com.example.petslist.service;

import com.example.petslist.error.APIError;
import com.example.petslist.error.APIException;
import com.example.petslist.model.User;
import com.example.petslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User newUser) {
        if (usernameExists(newUser.getUsername())) {
            throw new APIException(APIError.USER_ALREADY_EXIST.withParam(newUser.getUsername()));
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return save(newUser);
    }

    public boolean usernameExists(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void unlock(User user) {
        user.setAccountLocked(false);
        save(user);
    }

    public void lock(User user) {
        user.setAccountLocked(true);
        save(user);
    }
}
