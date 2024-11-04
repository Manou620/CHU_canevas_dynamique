package com.chu.canevas.dto.Personnel;

import java.time.Instant;

public record PresentPersonnelDto (
    String IM,
    String Nom,
    String NomService,
    Instant date_enregistrement,
    boolean isFisrtEntry,
    String nom_utilisateur
) {
}
