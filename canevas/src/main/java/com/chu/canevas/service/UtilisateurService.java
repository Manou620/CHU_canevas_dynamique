package com.chu.canevas.service;

import com.chu.canevas.model.Utilisateur;

public interface UtilisateurService {
    Utilisateur registerUser(Utilisateur utilisateur);

    Utilisateur getUtilisateurById(Long id);
}
