package com.chu.canevas.dto.TypeAbsence;

public record TypeAbsenceDto(
        Short id,
        Short duree_max_par_prise,
        Short duree_max_cumul_autorise,
        String frequence,
        String nom
) {
}
