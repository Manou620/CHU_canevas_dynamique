package com.chu.canevas.dto.Scan;

import com.chu.canevas.dto.Horaire.HoraireLigthDTO;
import com.chu.canevas.dto.Utilisateur.UtilisateurInfoDTO;
import com.chu.canevas.model.Personnel;

import java.time.Instant;

public record EntryCreationDTO(
        Personnel personnel,
        Instant moment_enregistrement,
        Boolean is_first_entry,
        Boolean is_late,
        HoraireLigthDTO horaire,
        UtilisateurInfoDTO utilisateur
) {
}
