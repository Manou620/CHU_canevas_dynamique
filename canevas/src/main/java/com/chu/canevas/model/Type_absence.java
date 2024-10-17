package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Type_absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id_type_absence;

    private Short duree_max_par_prise;

    private Short duree_max_cumul_autorise;

    private String frequence;

    private String nomTypeConge;

    @OneToMany(mappedBy = "typeAbsence")
    private List<Absence> absenceList;

}
