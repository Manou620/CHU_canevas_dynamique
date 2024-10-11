package com.chu.canevas.service.implementation;

import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Utilisateur;
import com.chu.canevas.repository.UtilisateurRepository;
import com.chu.canevas.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImplementation implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * @param utilisateur
     * @return
     */
    @Override
    public Utilisateur registerUser(Utilisateur utilisateur) {
        utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Utilisateur Introuvable")
        );
    }
}
