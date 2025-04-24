package com.example.atm.service;

import com.example.atm.dto.PetDto;
import com.example.atm.model.*;
import com.example.atm.repository.PetRepository;
import com.example.atm.service.exception.PetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public PetDto getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        return modelMapper.map(pet, PetDto.class);
    }

    @Transactional
    public PetDto createPet(PetDto petDto) {
        Pet pet = modelMapper.map(petDto, Pet.class);
        Pet savedPet = petRepository.save(pet);
        return modelMapper.map(savedPet, PetDto.class);
    }

    @Transactional(readOnly = true)
    public List<PetDto> getPetsByColor(Color color) {
        return petRepository.findByColor(color).stream()
                .map(pet -> modelMapper.map(pet, PetDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetDto> getPetsByBreed(Breed breed) {
        return petRepository.findByBreed(breed).stream()
                .map(pet -> modelMapper.map(pet, PetDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetDto> getPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId).stream()
                .map(pet -> modelMapper.map(pet, PetDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PetDto updatePet(Long id, PetDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        modelMapper.map(petDto, existingPet);
        Pet updatedPet = petRepository.save(existingPet);
        return modelMapper.map(updatedPet, PetDto.class);
    }

    @Transactional
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream()
                .map(pet -> modelMapper.map(pet, PetDto.class))
                .collect(Collectors.toList());
    }
}