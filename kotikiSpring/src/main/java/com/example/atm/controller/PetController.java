package com.example.atm.controller;

import com.example.atm.dto.PetDto;
import com.example.atm.model.Breed;
import com.example.atm.model.Color;
import com.example.atm.service.PetService;
import com.example.atm.service.exception.PetNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping("/all")
    public ResponseEntity<List<PetDto>> getAllPets() {
        try {
            List<PetDto> pets = petService.getAllPets();
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable Long id) {
        try {
            PetDto pet = petService.getPetById(id);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByColor/{color}")
    public ResponseEntity<List<PetDto>> getPetsByColor(@PathVariable Color color) {
        try {
            List<PetDto> pets = petService.getPetsByColor(color);
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByBreed/{breed}")
    public ResponseEntity<List<PetDto>> getPetsByBreed(@PathVariable Breed breed) {
        try {
            List<PetDto> pets = petService.getPetsByBreed(breed);
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByOwner/{ownerId}")
    public ResponseEntity<List<PetDto>> getPetsByOwner(@PathVariable Long ownerId) {
        try {
            List<PetDto> pets = petService.getPetsByOwner(ownerId);
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPet(@Valid @RequestBody PetDto petDto) {
        try {
            PetDto createdPet = petService.createPet(petDto);
            return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Long id,
                                       @Valid @RequestBody PetDto petDto) {
        try {
            PetDto updatedPet = petService.updatePet(id, petDto);
            return new ResponseEntity<>(updatedPet, HttpStatus.OK);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Pet with id={0} not found", id),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        try {
            petService.deletePet(id);
            return ResponseEntity.ok().body("Pet deleted successfully");
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Pet with id={0} not found", id),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}