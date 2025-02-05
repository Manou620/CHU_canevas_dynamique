package com.chu.canevas.dto.Personnel;

import com.chu.canevas.dto.Horaire.HoraireDTO;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.enums.Sexe;

public record HeavyPersonnelDto(
        String IM,
        String nom,
        String fonction,
        String photoPath,
        Sexe sexe,
        PersonnelDTO superieur,
        HoraireDTO horaire,
        ServiceDTO service
) {
}
