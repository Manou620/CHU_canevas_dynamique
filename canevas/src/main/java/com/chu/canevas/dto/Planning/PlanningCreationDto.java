package com.chu.canevas.dto.Planning;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PlanningCreationDto (

        @NotNull(message = "Le debut du planning doit etre specifié")
        LocalDateTime debut_heure,

        @NotNull(message = "La fin du planning doit etre specifié")
        LocalDateTime fin_heure,

        @NotBlank(message = "Le personnel est obligatoire")
        String immatriculation

) {
}
