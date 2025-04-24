package com.example.atm.service;

import com.example.atm.dto.FriendshipDto;
import com.example.atm.model.Friendship;
import com.example.atm.model.Pet;
import com.example.atm.repository.FriendshipRepository;
import com.example.atm.repository.PetRepository;
import com.example.atm.service.exception.PetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<FriendshipDto> getAllFriendships() {
        return friendshipRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FriendshipDto> getFriendshipsByPetId(Long petId) {
        return friendshipRepository.findAllFriendshipsById(petId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public FriendshipDto createFriendship(FriendshipDto friendshipDto) {
        if (friendshipDto.getPetId().equals(friendshipDto.getFriendId())) {
            throw new IllegalArgumentException("Pet cannot be friends with itself");
        }

        if (friendshipRepository.existsFriendship(friendshipDto.getPetId(), friendshipDto.getFriendId())) {
            throw new IllegalStateException("Friendship already exists");
        }

        Pet pet = petRepository.findById(friendshipDto.getPetId())
                .orElseThrow(() -> new PetNotFoundException(friendshipDto.getPetId()));

        Pet friend = petRepository.findById(friendshipDto.getFriendId())
                .orElseThrow(() -> new PetNotFoundException(friendshipDto.getFriendId()));

        Friendship friendship = new Friendship();
        friendship.setPet(pet);
        friendship.setFriend(friend);
        friendship.setFriendshipDate(LocalDate.now());

        Friendship saved = friendshipRepository.save(friendship);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteFriendship(Long id) {
        friendshipRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsFriendship(Long petId, Long friendId) {
        return friendshipRepository.existsFriendship(petId, friendId);
    }

    private FriendshipDto convertToDto(Friendship friendship) {
        FriendshipDto dto = modelMapper.map(friendship, FriendshipDto.class);
        dto.setPetId(friendship.getPet().getId());
        dto.setFriendId(friendship.getFriend().getId());
        return dto;
    }
}