package com.example.petslist.controller;

import com.example.petslist.model.Pet;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ModelAssembler implements RepresentationModelAssembler<Pet, EntityModel<Pet>> {

    @Override
    public EntityModel<Pet> toModel(Pet pet) {
        return EntityModel.of(pet,
                linkTo(methodOn(PetController.class).one(pet.getId())).withSelfRel(),
                linkTo(methodOn(PetController.class).all()).withRel("pets"));
    }
}
