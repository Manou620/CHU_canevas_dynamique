package com.chu.canevas.dto.Scan;

import com.chu.canevas.dto.Horaire.HoraireLigthDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.Utilisateur.UtilisateurInfoDTO;

import java.time.Instant;

public record SortieDTO(
        Long id,
        Instant moment_enregistrement,
        String observation,
        Boolean is_early,
        PersonnelDTO personnel,
        HoraireLigthDTO horaire,
        UtilisateurInfoDTO utilisateur,
        EntryLiteDTO associatedEntry
) {
}
