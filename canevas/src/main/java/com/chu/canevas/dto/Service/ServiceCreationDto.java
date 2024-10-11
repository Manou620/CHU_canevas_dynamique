package com.chu.canevas.dto.Service;

import jakarta.validation.constraints.NotBlank;

public record ServiceCreationDto(
        @NotBlank(message = "Le nom du service ne doit pas etre null")
        String nom_service,

        @NotBlank(message = "La description de doit pas etre null")
        String description
        
) {
}
