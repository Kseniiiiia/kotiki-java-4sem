package org.example;

import com.example.atm.dto.*;
import com.example.atm.model.*;
import com.example.atm.repository.*;
import com.example.atm.service.*;
import com.example.atm.service.exception.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OwnerService ownerService;

    @InjectMocks
    private PetService petService;

    @InjectMocks
    private FriendshipService friendshipService;

    @Test
    void createOwner_ShouldReturnOwnerDto() {
        OwnerDto dto = new OwnerDto();
        dto.setOwnerName("John");

        Owner owner = new Owner();
        owner.setId(1L);
        owner.setOwnerName("John");

        when(modelMapper.map(dto, Owner.class)).thenReturn(owner);
        when(ownerRepository.save(owner)).thenReturn(owner);
        when(modelMapper.map(owner, OwnerDto.class)).thenReturn(dto);

        OwnerDto result = ownerService.createOwner(dto);

        assertEquals("John", result.getOwnerName());
        verify(ownerRepository).save(owner);
    }

    @Test
    void getOwnerById_ShouldThrowWhenNotFound() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.getOwnerById(1L);
        });
    }

    @Test
    void createPet_ShouldReturnPetDto() {
        PetDto dto = new PetDto();
        dto.setPetName("Fluffy");
        dto.setColor(Color.BLACK);

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setPetName("Fluffy");

        when(modelMapper.map(dto, Pet.class)).thenReturn(pet);
        when(petRepository.save(pet)).thenReturn(pet);
        when(modelMapper.map(pet, PetDto.class)).thenReturn(dto);

        PetDto result = petService.createPet(dto);

        assertEquals("Fluffy", result.getPetName());
        verify(petRepository).save(pet);
    }

    @Test
    void getPetsByColor_ShouldReturnFilteredList() {
        Pet pet = new Pet();
        pet.setColor(Color.WHITE);

        when(petRepository.findByColor(Color.WHITE)).thenReturn(Collections.singletonList(pet));
        when(modelMapper.map(pet, PetDto.class)).thenReturn(new PetDto());

        List<PetDto> result = petService.getPetsByColor(Color.WHITE);

        assertEquals(1, result.size());
    }

    @Test
    void createFriendship_ShouldThrowWhenSamePet() {
        FriendshipDto dto = new FriendshipDto();
        dto.setPetId(1L);
        dto.setFriendId(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            friendshipService.createFriendship(dto);
        });
    }

    @Test
    void createFriendship_ShouldThrowWhenAlreadyExists() {
        FriendshipDto dto = new FriendshipDto();
        dto.setPetId(1L);
        dto.setFriendId(2L);

        when(friendshipRepository.existsFriendship(1L, 2L)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            friendshipService.createFriendship(dto);
        });

        verify(petRepository, never()).findById(anyLong());
    }

    @Test
    void deleteFriendship_ShouldCallRepository() {
        doNothing().when(friendshipRepository).deleteById(1L);

        friendshipService.deleteFriendship(1L);

        verify(friendshipRepository).deleteById(1L);
    }

    @Test
    void getPetsByOwner_ShouldReturnOwnerPets() {
        Owner owner = new Owner();
        owner.setId(1L);

        Pet pet = new Pet();
        pet.setOwner(owner);

        when(ownerRepository.existsById(1L)).thenReturn(true);
        when(petRepository.findByOwnerId(1L)).thenReturn(Collections.singletonList(pet));
        when(modelMapper.map(pet, PetDto.class)).thenReturn(new PetDto());

        List<PetDto> result = ownerService.getPetsByOwner(1L);

        assertEquals(1, result.size());
    }

    @Test
    void updatePet_ShouldUpdateExistingPet() {
        PetDto dto = new PetDto();
        dto.setPetName("Updated");
        dto.setColor(Color.BLACK);

        Pet existingPet = new Pet();
        existingPet.setId(1L);
        existingPet.setPetName("Original");
        existingPet.setColor(Color.WHITE);

        when(petRepository.findById(1L)).thenReturn(Optional.of(existingPet));

        doAnswer(invocation -> {
            PetDto source = invocation.getArgument(0);
            Pet destination = invocation.getArgument(1);
            destination.setPetName(source.getPetName());
            destination.setColor(source.getColor());
            return null;
        }).when(modelMapper).map(eq(dto), any(Pet.class));

        when(petRepository.save(existingPet)).thenReturn(existingPet);

        PetDto resultDto = new PetDto();
        resultDto.setPetName("Updated");
        resultDto.setColor(Color.BLACK);
        when(modelMapper.map(existingPet, PetDto.class)).thenReturn(resultDto);

        PetDto result = petService.updatePet(1L, dto);

        assertEquals("Updated", result.getPetName());
        assertEquals(Color.BLACK, result.getColor());
        verify(petRepository).save(existingPet);
    }
}