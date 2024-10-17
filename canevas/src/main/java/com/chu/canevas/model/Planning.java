package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planning { //Pour les horaires flexibles

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_planning;

    private LocalDate date_prevu;

    private final Instant date_definition_planning = Instant.now();

    private LocalDateTime debut_heure;
    private LocalDateTime fin_heure;

    @ManyToOne
    @JoinColumn(name = "personnel_IM")
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "horaire_id")
    private Horaire horaire;

}
