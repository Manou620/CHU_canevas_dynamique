package com.chu.canevas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nom_utilisateur;

    private String password;

    private Boolean enabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "personnel_im")
    private Personnel personnel;

//    @OneToMany(mappedBy = "utilisateur")
//    private List<Scan> scanList;
//
//    @OneToMany(mappedBy = "utilisateur_responsable")
//    private List<Autorisation_sortie> autorisationSortieList;

}
