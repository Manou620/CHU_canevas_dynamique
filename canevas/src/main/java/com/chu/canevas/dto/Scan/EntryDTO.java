package com.chu.canevas.dto.Scan;

import com.chu.canevas.dto.Horaire.HoraireLigthDTO;
import com.chu.canevas.dto.Personnel.PersonnelLiteDto;
import com.chu.canevas.dto.Utilisateur.UtilisateurInfoDTO;

import java.time.Instant;

public record EntryDTO (
    Long id,
    Instant moment_enregistrement,
    String observation,
    Boolean is_first_entry,
    Boolean is_late,
    HoraireLigthDTO horaire,
    SortieLightDTO sortie,
    UtilisateurInfoDTO utilisateur,
    PersonnelLiteDto personnel
){
}
