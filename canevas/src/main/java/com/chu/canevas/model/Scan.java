package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "SCAN_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class Scan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idScan;
    @Column(nullable = false, updatable = false)
    private final Instant dateEnregistrement = Instant.now();
    private String observation;
    @ManyToOne
    @JoinColumn(name = "personnel_IM")
    private Personnel personnel;
    @ManyToOne
    @JoinColumn(name = "utilisateur_scanneur")
    private Utilisateur utilisateur;

    private String heureAttendu = null;
}
