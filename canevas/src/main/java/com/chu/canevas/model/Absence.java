package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_absence;

    private LocalDateTime debut_absence;
    private LocalDateTime fin_absence;

    private String motif;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private Type_absence typeAbsence;

    @ManyToOne
    @JoinColumn(name = "personnel_IM")
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "interim_IM")
    private Personnel interim;

}
