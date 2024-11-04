package com.chu.canevas.dto.Personnel;

import com.chu.canevas.enums.Sexe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonnelCreationDTO(

        @Size(min = 6, max = 6, message = "L'immatriculation doit avoir 6 nombre")
        @NotBlank(message = "L'immatriculation ne de doit pas etre null")
        String IM,

        @NotBlank(message = "Le nom ne de doit pas etre null")
        String nom,

        @NotBlank(message = "Le fonction ne de doit pas etre null")
        String fonction,
        
        String PhotoPath,

        String superieur_id,

        @NotNull(message = "L'employe doit avoir un horaire")
        Short horaire_id,

        @NotBlank(message = "Le genre de l'employe doit etre specifié")
        Sexe sexe,

        @NotNull
        Short service_id

) {
}
