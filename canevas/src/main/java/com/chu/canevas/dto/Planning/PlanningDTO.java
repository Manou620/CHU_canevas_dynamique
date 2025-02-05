package com.chu.canevas.dto.Planning;

import com.chu.canevas.enums.Sexe;
import com.chu.canevas.model.Planning;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanningDTO {

    private Long idPlanning;
    private String debutHeure;
    private String finHeure;
    private String personnelNom;
    private String personnelImmatriculation;
    private String personnelService;
    private String personnelFunction;
    private Sexe personnelSexe;


    public PlanningDTO (Planning planning) {
        this.idPlanning = planning.getId_planning();
        this.debutHeure = planning.getDebut_heure().toString();
        this.finHeure = planning.getFin_heure().toString();
        this.personnelNom = planning.getPersonnel().getNom();
        this.personnelImmatriculation = planning.getPersonnel().getImmatriculation();
        this.personnelService = planning.getPersonnel().getService().getNomService();
        this.personnelFunction  =planning.getPersonnel().getFonction();
        this.personnelSexe  =planning.getPersonnel().getSexe();
    }
}
