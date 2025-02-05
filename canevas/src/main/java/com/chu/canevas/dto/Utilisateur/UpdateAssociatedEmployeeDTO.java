package com.chu.canevas.dto.Utilisateur;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAssociatedEmployeeDTO {
    @NotBlank(message = "Le nouveau employée associé est obligatoire")
    private String immatriculation;
}
