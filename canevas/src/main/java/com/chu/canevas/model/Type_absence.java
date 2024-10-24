package com.chu.canevas.model;

import com.chu.canevas.enums.TypeAbsenceFrequencyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Type_absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id_type_absence;

    private Short duree_max_par_prise;

    private Short duree_max_cumul_autorise;

    @Enumerated(EnumType.STRING)
    private TypeAbsenceFrequencyEnum frequence;

    private String nomTypeConge;

    @OneToMany(mappedBy = "typeAbsence")
    private List<Absence> absenceList;

}
