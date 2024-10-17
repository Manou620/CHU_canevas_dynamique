package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Personnel {

    @Id
    @Column(length = 6)
    private String immatriculation;

    private String nom;

    @Column(length = 150)
    private String fonction;

    private String photoPath;

    @OneToOne(mappedBy = "personnel") //non du attribut mais pas du colonne
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "superieur_im")
    private Personnel superieur;

    @OneToMany(mappedBy = "superieur")
    private List<Personnel> subordonee;

    @OneToMany(mappedBy = "personnel")
    private List<Scan> scan;

//    @OneToMany(mappedBy = "personnel")
//    private List<Assignation_horaire> assignation_horaires;

    @ManyToOne
    @JoinColumn(name = "horaire_id")
    private Horaire horaire;

    @OneToMany(mappedBy = "personnel")
    private List<Planning> plannings;

//    @OneToMany(mappedBy = "personnel")
//    private List<Autorisation_sortie> autorisationSortieList;

    @OneToMany(mappedBy = "personnel")
    private List<Absence> absenceList;

    @OneToMany(mappedBy = "interim")
    private List<Absence> interimList;

}
