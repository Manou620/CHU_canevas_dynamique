package com.chu.canevas.dto.Utilisateur;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUsername {
    @NotBlank(message = "Le nouveau nom est obligatoire")
    private String newUsername;
}
