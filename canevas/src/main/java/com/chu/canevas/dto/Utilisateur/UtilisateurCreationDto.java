package com.chu.canevas.dto.Utilisateur;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurCreationDto {

    @NotBlank(message = "Le nom d'utilisateur ne doit pas etre null")
    private String nom_utilisateur;

    @NotBlank(message = "Le mot de passe ne doit pas etre vide")
    private String password;

    private Boolean enabled;

    @NotBlank(message = "L'employé associé ne doit pas etre vide")
    private String personnel_id;

    @NotNull(message = "Le role doit etre specifié")
    private Long role_id;

    @NotBlank(message = "Le mot de passe ne doit pas etre vide")
    private String checkPassword;

}
