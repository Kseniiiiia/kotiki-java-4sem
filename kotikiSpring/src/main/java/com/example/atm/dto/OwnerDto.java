package com.example.atm.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor   // (access = AccessLevel.PRIVATE)
public final class OwnerDto {
    @Null(message = "ID should be null for creation")
    private Long id;

    @Past(message = "Birth date must be in past")
    private LocalDate ownerBirthDate;

    @NotBlank(message = "Owner name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String ownerName;
}

