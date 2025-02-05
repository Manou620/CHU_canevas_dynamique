package com.chu.canevas.dto.Utilisateur;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEnabledDTO {
    @NotNull(message = "Le status doit etre obligatoire")
    private Boolean enabledValue;
}
