package com.chu.canevas.service.implementation;

import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Utilisateur;
import com.chu.canevas.repository.UtilisateurRepository;
import com.chu.canevas.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username)
                .orElseThrow(() -> new ElementNotFoundException("Utilisateur introuvable"));

//        System.out.println(utilisateur.getNom_utilisateur() + utilisateur.getPassword());
//        if(utilisateur.getPassword() == null || utilisateur.getPassword().isEmpty()){
//            System.out.println("User's password is empty");
//        }else{
//            System.out.println("Password : " + utilisateur.getPassword());
//        }

        return new UserDetailsImpl(//Attention au getter et setter
                utilisateur.getId(),
                utilisateur.getNom_utilisateur(),
                utilisateur.getPassword(),
                Collections.singletonList(utilisateur.getRole())
        );
    }
}
