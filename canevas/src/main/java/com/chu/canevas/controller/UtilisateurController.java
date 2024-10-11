package com.chu.canevas.controller;

import com.chu.canevas.model.Utilisateur;
import com.chu.canevas.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/register")
    public Utilisateur enregistrer(@RequestBody Utilisateur utilisateur){
        return utilisateurService.registerUser(utilisateur);
    }

    @GetMapping("/{id}")
    public Utilisateur getUtilisateurById(@PathVariable Long id){
        return utilisateurService.getUtilisateurById(id);
    }
}
