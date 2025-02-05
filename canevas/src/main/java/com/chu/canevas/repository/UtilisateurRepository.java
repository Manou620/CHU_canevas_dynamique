package com.chu.canevas.repository;

import com.chu.canevas.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>, JpaSpecificationExecutor<Utilisateur> {
    @Query("SELECT u FROM Utilisateur u WHERE u.nom_utilisateur = ?1")
    Optional<Utilisateur> findUtilisateurByUsername(String nom_utilisateur);

    @Query("SELECT u FROM Utilisateur u WHERE u.nom_utilisateur = ?1 AND u.password = ?2")
    Optional<Utilisateur> findUtilisateurByUsernameAndPassword(String nom_utilisateur, String password);

    @Query("SELECT COUNT(u) > 0 FROM Utilisateur u WHERE u.nom_utilisateur = ?1")
    boolean existsByUsername(String nom_utilisateur);

    @Query("SELECT COUNT(u) > 0 FROM Utilisateur u WHERE u.personnel.immatriculation = ?1")
    boolean existsByPersonnelImmatriculation(String immatriculation);


}
