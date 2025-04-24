package com.example.atm.controller;

import com.example.atm.dto.OwnerDto;
import com.example.atm.dto.PetDto;
import com.example.atm.service.OwnerService;
import com.example.atm.service.exception.OwnerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/all")
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        try {
            List<OwnerDto> owners = ownerService.getAllOwners();
            return new ResponseEntity<>(owners, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable Long id) {
        try {
            OwnerDto owner = ownerService.getOwnerById(id);
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } catch (OwnerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPets/{ownerId}")
    public ResponseEntity<List<PetDto>> getPetsByOwner(@PathVariable Long ownerId) {
        try {
            List<PetDto> pets = ownerService.getPetsByOwner(ownerId);
            return ResponseEntity.ok(pets);
        } catch (OwnerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOwner(@Valid @RequestBody OwnerDto ownerDto) {
        try {
            OwnerDto createdOwner = ownerService.createOwner(ownerDto);
            return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id,
                                         @Valid @RequestBody OwnerDto ownerDto) {
        try {
            OwnerDto updatedOwner = ownerService.updateOwner(id, ownerDto);
            return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
        } catch (OwnerNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Owner with id={0} not found", id),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        try {
            ownerService.deleteOwner(id);
            return ResponseEntity.ok().body("Owner deleted successfully");
        } catch (OwnerNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Owner with id={0} not found", id),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}