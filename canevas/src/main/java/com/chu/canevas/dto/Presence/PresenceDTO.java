package com.chu.canevas.dto.Presence;

import com.chu.canevas.model.Entry;
import com.chu.canevas.model.Personnel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresenceDTO {

    private String employeeImmatriculation;
    private String employeeName;
    private String employeeFunction;
    private Short employeeServiceId;
    private String employeeServiceName;
    private Instant dateEntree;
    private Instant dateSortie = null;

    public PresenceDTO (Entry entry) {
        Personnel personnel = entry.getPersonnel();
        this.employeeImmatriculation = personnel.getImmatriculation();
        this.employeeName = personnel.getNom();
        this.employeeFunction = personnel.getFonction();
        this.employeeServiceId = personnel.getService().getId();
        this.employeeServiceName = personnel.getService().getNomService();
        this.dateEntree = entry.getDateEnregistrement();
        this.dateSortie = entry.getAnswer_sortie() != null ? entry.getAnswer_sortie().getDateEnregistrement() : null;
    }

}
