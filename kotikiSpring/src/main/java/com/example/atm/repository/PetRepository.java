package com.example.atm.repository;

import com.example.atm.model.Breed;
import com.example.atm.model.Color;
import com.example.atm.model.Pet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByColor(Color color);

    List<Pet> findByBreed(Breed breed);

    List<Pet> findByOwnerId(Long ownerId);
    //Page<Pet> findByOwnerId(Long ownerId, Pageable pageable);
}
