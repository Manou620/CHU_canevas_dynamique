package com.chu.canevas.dto.Utilisateur;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    private String newPassword;
}
