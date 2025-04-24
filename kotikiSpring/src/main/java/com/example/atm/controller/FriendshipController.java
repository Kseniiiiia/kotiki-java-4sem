package com.example.atm.controller;

import com.example.atm.dto.FriendshipDto;
import com.example.atm.service.FriendshipService;
import com.example.atm.service.exception.FriendshipNotFoundException;
import com.example.atm.service.exception.PetNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @GetMapping("/all")
    public ResponseEntity<List<FriendshipDto>> getAllFriendships() {
        try {
            List<FriendshipDto> friendships = friendshipService.getAllFriendships();
            return new ResponseEntity<>(friendships, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<?> getFriendshipsByPet(@PathVariable Long petId) {
        try {
            List<FriendshipDto> friendships = friendshipService.getFriendshipsByPetId(petId);
            return new ResponseEntity<>(friendships, HttpStatus.OK);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Pet with id={0} not found", petId),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFriendship(@Valid @RequestBody FriendshipDto friendshipDto) {
        try {
            FriendshipDto createdFriendship = friendshipService.createFriendship(friendshipDto);
            return new ResponseEntity<>(createdFriendship, HttpStatus.CREATED);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Pet not found: {0}", e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }  catch (FriendshipNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Friendship not found: {0}", e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFriendship(@PathVariable Long id) {
        try {
            friendshipService.deleteFriendship(id);
            return ResponseEntity.ok().body("Friendship deleted successfully");
        }  catch (FriendshipNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Friendship not found: {0}", e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<?> checkFriendshipExists(
            @RequestParam("petId") Long petId,
            @RequestParam("friendId") Long friendId) {
        try {
            boolean exists = friendshipService.existsFriendship(petId, friendId);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(
                    MessageFormat.format("Pet not found: {0}", e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error checking friendship: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}