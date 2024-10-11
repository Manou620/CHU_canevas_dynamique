package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

//@Entity
@Getter
@Setter
@NoArgsConstructor
public class Assignation_horaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_assignation;

    private LocalDate debut_assignation;

    @Column(nullable = true)
    private LocalDate fin_assignation;

    @OneToOne
    @JoinColumn(name = "personnel_IM")
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "horaire_id")
    private Horaire horaire;

}
