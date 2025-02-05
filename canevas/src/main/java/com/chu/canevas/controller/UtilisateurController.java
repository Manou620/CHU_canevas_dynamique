package com.chu.canevas.controller;

import com.chu.canevas.dto.Utilisateur.*;
import com.chu.canevas.model.Utilisateur;
import com.chu.canevas.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Validated
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

    @GetMapping("/dto/{id}")
    public UtilisateurDTO getUtilisateurDTOById(@PathVariable Long id){
        return utilisateurService.getUserDtoById(id);
    }

    @GetMapping
    public ResponseEntity<Page<UtilisateurDTO>> getUtilisateurs(
            @RequestParam(required = false) String id_nom,
            @RequestParam(required = false) Long role,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(defaultValue = "nom_utilisateur") String sortBy,
            @RequestParam (defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ){
        Page<UtilisateurDTO> utilisateurDTOS = utilisateurService.getUtilisateurs(
                id_nom, role, enabled, page, size, sortBy, sortDirection
        );
        return new ResponseEntity<>(utilisateurDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUSer (@RequestBody @Validated UtilisateurCreationDto utilisateurCreationDto, @PathVariable Long id){
        return new ResponseEntity<>(utilisateurService.updateUser(utilisateurCreationDto, id), HttpStatus.OK);
    }

    @PutMapping("/update-username/{id}")
    public ResponseEntity<UtilisateurDTO> updateUserName (@RequestBody @Validated UpdateUsername updateUsername, @PathVariable Long id){
        return new ResponseEntity<>(utilisateurService.updateUsername(updateUsername.getNewUsername(), id), HttpStatus.OK);
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<UtilisateurDTO> updatePassword (@RequestBody @Validated UpdatePasswordDTO updatePasswordDTO, @PathVariable Long id){
        return new ResponseEntity<>(utilisateurService.updatePassword(id, updatePasswordDTO.getNewPassword()), HttpStatus.OK);
    }

    @PutMapping("/update-associated-employee/{id}")
    public ResponseEntity<UtilisateurDTO> updateEmployee(@RequestBody @Validated UpdateAssociatedEmployeeDTO updateAssociatedEmployeeDTO, @PathVariable Long id){
        return new ResponseEntity<>(utilisateurService.changeAssociatedEmploye(id, updateAssociatedEmployeeDTO.getImmatriculation()), HttpStatus.OK);
    }

    @PutMapping("/activate-desactivate/{id}")
    public ResponseEntity<UtilisateurDTO> activateOrDesactivateUser (@RequestBody @Validated UpdateEnabledDTO updateEnabledDTO, @PathVariable Long id){
        return new ResponseEntity<>(utilisateurService.activateDesactivateUser(id, updateEnabledDTO.getEnabledValue()), HttpStatus.OK);
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<UtilisateurDTO> updateRole (@RequestBody @Validated UpdateRoleDTO updateRoleDTO, @PathVariable Long id){
        return new ResponseEntity<>(utilisateurService.updateRole(id, updateRoleDTO.getNewRole()), HttpStatus.OK);
    }
}
