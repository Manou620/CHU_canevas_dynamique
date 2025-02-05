package com.chu.canevas.dto.Utilisateur;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDTO {
    @NotNull(message = "Le nouveau role ne doit pas etre null")
    private Long newRole;
}
