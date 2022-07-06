package com.example.petslist.repository;

import com.example.petslist.model.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptsRepository extends JpaRepository<Attempts, Long> {
}
