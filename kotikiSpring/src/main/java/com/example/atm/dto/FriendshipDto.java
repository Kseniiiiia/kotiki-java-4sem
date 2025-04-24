package com.example.atm.dto;

import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class FriendshipDto {
    @Null(message = "ID should be null for creation")
    private Long id;

    @NotNull(message = "Pet ID is required")
    @Positive(message = "Pet ID must be positive")
    private Long petId;

    @NotNull(message = "Friend ID is required")
    @Positive(message = "Friend ID must be positive")
    private Long friendId;

    @Null(message = "Date should be auto-generated")
    private LocalDate friendshipDate;
}
