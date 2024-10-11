package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//@Entity
@Getter
@Setter
@NoArgsConstructor
public class Autorisation_sortie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_autorisation;

    private Short duree_estime;

    private LocalDate date_effectif;

    private String motif;

    @Column(length = 100)
    private String lieu;

    @ManyToOne
    private Personnel personnel;

    @ManyToOne
    private Utilisateur utilisateur_responsable;

    @OneToOne(mappedBy = "autorisationSortie")
    private Sortie sortie;

}
