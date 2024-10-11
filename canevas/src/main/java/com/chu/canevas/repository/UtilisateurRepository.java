package com.chu.canevas.repository;

import com.chu.canevas.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query("SELECT u FROM Utilisateur u WHERE u.nom_utilisateur = ?1")
    Optional<Utilisateur> findUtilisateurByUsername(String nom_utilisateur);

}
