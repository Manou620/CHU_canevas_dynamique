package com.chu.canevas.dto.Horaire;

import java.time.LocalTime;

public record HoraireDTO(
        short id_horaire,
        String libelle_horaire,
        LocalTime debut_horaire,
        LocalTime fin_horaire,
        boolean flexible
) {
}
