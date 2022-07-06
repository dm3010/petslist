package com.example.petslist.controller;

import com.example.petslist.error.PetNotFoundException;
import com.example.petslist.model.Pet;
import com.example.petslist.model.PetType;
import com.example.petslist.repository.PetRepository;
import com.example.petslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ModelAssembler assembler;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/pets")
    CollectionModel<EntityModel<Pet>> all() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<EntityModel<Pet>> pets = petRepository
                .findAllByUser_Username(username)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pets,
                linkTo(methodOn(PetController.class).all()).withSelfRel(),
                linkTo(methodOn(PetController.class).petsTypes()).withRel("petsTypes"));
    }

    @GetMapping("/pets/{id}")
    EntityModel<Pet> one(@PathVariable Long id) {

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        return assembler.toModel(pet);
    }


    @PostMapping("/pets")
    ResponseEntity<?> newPet(@RequestBody Pet newPet) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newPet.setUser(userRepository.findUserByUsername(username));

        EntityModel<Pet> entityModel = assembler.toModel(petRepository.save(newPet));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/pets/{id}")
    ResponseEntity<?> replacePet(@RequestBody Pet newPet, @PathVariable Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newPet.setUser(userRepository.findUserByUsername(username));

        Pet updatedPet = petRepository.findById(id)
                .map(pet -> {
                    if (!pet.getUser().getUsername().equals(username))
                        throw new RuntimeException("This pet for another user" + id);
                    pet.setName(newPet.getName());
                    pet.setBirthday(newPet.getBirthday());
                    pet.setType(newPet.getType());
                    return petRepository.save(pet);
                })
                .orElseGet(() -> {
                    newPet.setId(id);
                    return petRepository.save(newPet);
                });

        EntityModel<Pet> entityModel = assembler.toModel(updatedPet);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/pets/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        petRepository.deleteByIdAndUser_Username(id, username);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pets_types")
    CollectionModel<PetType> petsTypes() {

        List<PetType> petTypes = Arrays.stream(PetType.values()).collect(Collectors.toList());

        return CollectionModel.of(petTypes,
                linkTo(methodOn(PetController.class).petsTypes()).withSelfRel(),
                linkTo(methodOn(PetController.class).all()).withRel("pets"));
    }
}
