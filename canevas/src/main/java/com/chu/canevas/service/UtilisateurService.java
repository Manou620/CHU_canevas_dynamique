package com.chu.canevas.service;

import com.chu.canevas.dto.Utilisateur.UtilisateurCreationDto;
import com.chu.canevas.dto.Utilisateur.UtilisateurDTO;
import com.chu.canevas.model.Utilisateur;
import org.springframework.data.domain.Page;

public interface UtilisateurService {
    Utilisateur registerUser(Utilisateur utilisateur);

    Utilisateur getUtilisateurById(Long id);

    Page<UtilisateurDTO> getUtilisateurs (
            String id_nom, Long role, Boolean enabled, int page, int size, String sortBy, String sortDirection
    );

    UtilisateurDTO getUserDtoById (Long id);

    UtilisateurDTO updateUser (UtilisateurCreationDto utilisateurCreationDto, Long id);

    UtilisateurDTO updateUsername (String newUsername, Long id);

    UtilisateurDTO updatePassword (Long id, String newPassword);

    UtilisateurDTO activateDesactivateUser(Long id, Boolean value);

    UtilisateurDTO changeAssociatedEmploye(Long id, String immatriculation);

    UtilisateurDTO updateRole(Long id, Long role_id);
}
