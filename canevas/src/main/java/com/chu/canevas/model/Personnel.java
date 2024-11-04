package com.chu.canevas.model;

import com.chu.canevas.dto.Personnel.PersonnelCreationDTO;
import com.chu.canevas.enums.Sexe;
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

    public Personnel(Personnel personnel){
        this.nom = personnel.getNom();
        this.immatriculation = personnel.getImmatriculation();
        this.service = personnel.getService();
    }

    public Personnel (String IM) {
        this.immatriculation = IM;
    }

    public Personnel (PersonnelCreationDTO personnelCreationDTO) {
        this.immatriculation = personnelCreationDTO.IM();
        this.nom = personnelCreationDTO.nom();
        this.fonction = personnelCreationDTO.fonction();
        this.photoPath = personnelCreationDTO.PhotoPath();
        this.sexe = personnelCreationDTO.sexe();
        this.service = new Service(personnelCreationDTO.service_id());
        this.horaire = new Horaire(personnelCreationDTO.horaire_id());
        this.superieur = new Personnel(personnelCreationDTO.superieur_id());
    }

    @Id
    @Column(length = 6)
    private String immatriculation;

    private String nom;

    @Column(length = 150)
    private String fonction;

    private String photoPath;

    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "superieur_im")
    private Personnel superieur;

    @ManyToOne
    @JoinColumn(name = "horaire_id")
    private Horaire horaire;

    @OneToOne(mappedBy = "personnel") //non du attribut mais pas du colonne
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "superieur")
    private List<Personnel> subordonee;

    @OneToMany(mappedBy = "personnel")
    private List<Scan> scan;

//    @OneToMany(mappedBy = "personnel")
//    private List<Assignation_horaire> assignation_horaires;


    @OneToMany(mappedBy = "personnel")
    private List<Planning> plannings;

//    @OneToMany(mappedBy = "personnel")
//    private List<Autorisation_sortie> autorisationSortieList;

    @OneToMany(mappedBy = "personnel")
    private List<Absence> absenceList;

    @OneToMany(mappedBy = "interim")
    private List<Absence> interimList;

}
