package com.example.petslist.service;

import com.example.petslist.error.UserAlreadyExistException;
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

    public User register(User newUser) throws UserAlreadyExistException {
        if (usernameExists(newUser.getUsername())) {
            throw new UserAlreadyExistException(newUser.getUsername());
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public boolean usernameExists(String username) {
        return userRepository.findUserByUsername(username) != null;
    }
}
