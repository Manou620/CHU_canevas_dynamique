package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Horaire {

    public  Horaire (Short id_horaire){
        this.id_horaire = id_horaire;
    }

    public Short  getId(){
        return this.id_horaire;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id_horaire;

    @Column(length = 100, nullable = false)
    private String libelle_horaire;

    private LocalTime debut_horaire;

    private LocalTime fin_horaire;

    private Boolean flexible;

//    @OneToMany(mappedBy = "horaire")
//    private List<Assignation_horaire> assignation_horaire_list;

    @OneToMany(mappedBy = "horaire")
    private List<Personnel> personnels;

    @OneToMany(mappedBy = "horaire")
    private List<Planning> planningList;
}
