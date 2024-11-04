package com.chu.canevas.dto.Personnel;

import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.enums.Sexe;
import com.chu.canevas.model.Service;
import com.chu.canevas.model.Utilisateur;
import jakarta.persistence.*;

import java.util.List;

public record PersonnelDTO(
                String immatriculation,
                String nom,
                String fonction,
                String photoPath,
                ServiceDTO service,
                Sexe sexe
) {
}
