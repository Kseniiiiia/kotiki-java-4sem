package com.example.atm.service;

import com.example.atm.dto.OwnerDto;
import com.example.atm.dto.PetDto;
import com.example.atm.model.Owner;
import com.example.atm.repository.OwnerRepository;
import com.example.atm.repository.PetRepository;
import com.example.atm.service.exception.OwnerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(owner -> modelMapper.map(owner, OwnerDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OwnerDto getOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));
        return modelMapper.map(owner, OwnerDto.class);
    }

    @Transactional
    public OwnerDto createOwner(OwnerDto ownerDto) {
        Owner owner = modelMapper.map(ownerDto, Owner.class);
        Owner savedOwner = ownerRepository.save(owner);
        return modelMapper.map(savedOwner, OwnerDto.class);
    }

    @Transactional
    public OwnerDto updateOwner(Long id, OwnerDto ownerDto) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));

        modelMapper.map(ownerDto, existingOwner);
        Owner updatedOwner = ownerRepository.save(existingOwner);
        return modelMapper.map(updatedOwner, OwnerDto.class);
    }

    @Transactional
    public void deleteOwner(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new OwnerNotFoundException(id);
        }
        ownerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OwnerDto> findOwnersByName(String name) {
        return ownerRepository.findByOwnerName(name).stream()
                .map(owner -> modelMapper.map(owner, OwnerDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetDto> getPetsByOwner(Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new OwnerNotFoundException(ownerId);
        }

        return petRepository.findByOwnerId(ownerId).stream()
                .map(pet -> modelMapper.map(pet, PetDto.class))
                .collect(Collectors.toList());
    }
}