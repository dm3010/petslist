package com.example.petslist.security;

import com.example.petslist.model.Attempts;
import com.example.petslist.model.User;
import com.example.petslist.repository.AttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptsService {
    private final int MAX_ATTEMPT = 10;
    private final long TIME_TO_UNLOCK = 60 * 60000000000L;

    @Autowired
    private AttemptsRepository attemptsRepository;

    public void loginFailed(User user) {
        Attempts attempts = attemptsRepository
                .findById(user.getId())
                .orElse(new Attempts(user));

        attempts.setCount(attempts.getCount() + 1);
        attempts.setTime(System.nanoTime());
        attemptsRepository.save(attempts);
    }

    public void loginSucceeded(User user) {
        if (attemptsRepository.existsById(user.getId())) {
            attemptsRepository.deleteById(user.getId());
        }
    }

    public boolean isUserLockTimeExpired(User user) {
        return attemptsRepository
                .findById(user.getId())
                .map(attempts -> {
                            if (attempts.getTime() + TIME_TO_UNLOCK < System.nanoTime()) {
                                loginSucceeded(user);
                                return true;
                            } else return false;
                        }
                ).orElse(true);
    }

    public boolean isUserMaxAttemptsReached(User user) {
        return attemptsRepository.findById(user.getId())
                .map(attempts -> attempts.getCount() >= MAX_ATTEMPT)
                .orElse(false);
    }
}
