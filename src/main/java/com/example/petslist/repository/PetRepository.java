package com.example.petslist.repository;

import com.example.petslist.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUser_Username(String user_username);
    @Transactional
    void deleteByIdAndUser_Username(Long id, String user_username);
}
