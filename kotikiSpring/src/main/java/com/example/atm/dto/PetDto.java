package com.example.atm.dto;

import com.example.atm.model.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor   // (access = AccessLevel.PRIVATE)
public final class PetDto {
    @Null(message = "ID should be null for creation")
    private Long id;

    @NotBlank(message = "Pet name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String petName;

    @NotNull(message = "Birth date is required")
    @PastOrPresent(message = "Birth date must be in past or present")
    private LocalDate petBirthDate;

    @NotNull(message = "Breed is required")
    private Breed breed;

    @NotNull(message = "Color is required")
    private Color color;

    @NotNull(message = "Owner is required")
    private OwnerDto owner;
/*
    @NotBlank(message = "Gender is required")
    private Gender petGender;   // new param

 */
}
