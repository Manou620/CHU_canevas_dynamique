package com.chu.canevas.dto.Utilisateur;

import com.chu.canevas.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDTO {

    private Long id;
    private String nom_utilisateur;
    private Boolean enabled;
    private Long role_id;
    private String nom_role;
    private String user_im;
    private String nom_user_employee;
    private String employee_function;
    private Short service_id;
    private String service_name;
    private String password;

    public UtilisateurDTO (Utilisateur utilisateur){
        this.id = utilisateur.getId();
        this.nom_utilisateur = utilisateur.getNom_utilisateur();
        this.enabled = utilisateur.getEnabled();
        this.role_id = utilisateur.getRole().getId();
        this.nom_role = utilisateur.getRole().getNom_role();
        this.user_im = utilisateur.getPersonnel().getImmatriculation();
        this.employee_function = utilisateur.getPersonnel().getFonction();
        this.nom_user_employee = utilisateur.getPersonnel().getNom();
        this.service_id = utilisateur.getPersonnel().getService().getId();
        this.service_name = utilisateur.getPersonnel().getService().getNomService();
        this.password = utilisateur.getPassword();
    }

}
