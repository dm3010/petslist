package com.example.petslist;

import com.example.petslist.model.Pet;
import com.example.petslist.model.User;
import com.example.petslist.repository.PetRepository;
import com.example.petslist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.petslist.model.PetType.Cat;
import static com.example.petslist.model.PetType.Dog;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PetRepository petRepository, UserRepository userRepository) {

        return args -> {
            User user = new User("admin", new BCryptPasswordEncoder().encode("123"));
            log.info("Preloading " + userRepository.save(user));

            Pet dog = new Pet("Jassy", "12-05-2008", Dog);
            dog.setUser(user);
            petRepository.save(dog);

            Pet cat = new Pet("Kuzya", "15-07-2010", Cat);
            cat.setUser(user);
            petRepository.save(cat);

        };
    }
}
