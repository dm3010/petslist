package com.example.petslist.controller;

import com.example.petslist.error.APIError;
import com.example.petslist.error.APIException;
import com.example.petslist.model.Pet;
import com.example.petslist.model.PetType;
import com.example.petslist.repository.PetRepository;
import com.example.petslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/pets/my")
    List<Pet> my() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Pet> my = petRepository.findAllByUser_Username(username);

        if (my.isEmpty())
            throw new APIException(APIError.PET_LIST_IS_EMPTY.withParam(username));

        return my;
    }


    @GetMapping("/pets")
    List<Pet> all() {

        List<Pet> all = petRepository.findAll();

        if (all.isEmpty())
            throw new APIException(APIError.PET_LIST_IS_EMPTY.withParam("all"));

        return all;
    }

    @GetMapping("/pets/{id}")
    Pet one(@PathVariable Long id) {

        return petRepository.findById(id)
                .orElseThrow(() -> new APIException(APIError.PET_NOT_FOUND.withParam(String.valueOf(id))));
    }

    @PostMapping("/pets")
    ResponseEntity<Pet> newPet(@RequestBody Pet newPet) {

        if (petRepository.existsByName(newPet.getName()))
            throw new APIException(APIError.PET_ALREADY_EXIST.withParam(newPet.getName()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newPet.setUser(userService.findByUsername(username));

        return ResponseEntity.status(HttpStatus.CREATED).body(petRepository.save(newPet));
    }

    @PutMapping("/pets/{id}")
    Pet replacePet(@RequestBody Pet newPet, @PathVariable Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newPet.setUser(userService.findByUsername(username));

        return petRepository.findById(id)
                .map(pet -> {

                    if (!pet.getUser().getUsername().equals(username))
                        throw new APIException(APIError.PET_OWNER_CONFLICT.withParam(id));

                    pet.setName(newPet.getName());
                    pet.setBirthday(newPet.getBirthday());
                    pet.setType(newPet.getType());
                    return petRepository.save(pet);
                })
                .orElseGet(() -> {
                    newPet.setId(id);
                    newPet.setUser(userService.findByUsername(username));

                    return petRepository.save(newPet);
                });
    }

    @DeleteMapping("/pets/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new APIException(APIError.PET_NOT_FOUND.withParam(String.valueOf(id))));

        if (!pet.getUser().getUsername().equals(username))
            throw new APIException(APIError.PET_OWNER_CONFLICT.withParam(id));

        petRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Pet " + pet.getName() + " with id " + id + " deleted"));
    }

    @GetMapping("/pets_types")
    List<PetType> petsTypes() {

        return Arrays.stream(PetType.values()).collect(Collectors.toList());
    }
}
