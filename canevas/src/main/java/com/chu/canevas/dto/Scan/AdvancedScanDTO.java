package com.chu.canevas.dto.Scan;

import com.chu.canevas.model.Entry;
import com.chu.canevas.model.Scan;
import com.chu.canevas.model.Sortie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedScanDTO {

    private Long idScan;
    private String scanType; // "ENTRY" or "EXIT"
    private String observation;
    private String personnelName; // Combine personnel first and last name
    private String personnel_service_name;
    private String immatriculation;
    private Boolean isLateEntry = null;
    private Boolean isEarlyExit = null;
    private Boolean isFirstEntry = null;
    private Long AgentId;
    private String AgentName;
    private Instant dateEntregistrement;

    private String TypeHoraireAttendu = null;
    private  String HeureAttendu = null;

    private Long idAssociatedEntry = null;
    private Instant dateAssociatedEntry = null;

    public AdvancedScanDTO (Scan scan){
        this.idScan = scan.getIdScan();
        this.observation = scan.getObservation() != null ? scan.getObservation() : null;
        this.personnelName = scan.getPersonnel().getNom();
        this.personnel_service_name = scan.getPersonnel().getService().getNomService();
        this.immatriculation = scan.getPersonnel().getImmatriculation();
        this.AgentName = scan.getUtilisateur().getNom_utilisateur();
        this.AgentId = scan.getUtilisateur().getId();
        this.dateEntregistrement = scan.getDateEnregistrement();

        if(scan.getHeureAttendu() != null){
            String[] heureAttenduDetails = scan.getHeureAttendu().split(" ");
            this.TypeHoraireAttendu = heureAttenduDetails[1];
            this.HeureAttendu = heureAttenduDetails[2];
        }

        if(scan instanceof Entry){
            Entry entry = (Entry) scan;
            this.scanType = "ENTRY";
            this.isFirstEntry = entry.getFirst_entry();
            this.isLateEntry = entry.getIs_late();
        }

        if(scan instanceof Sortie){
            Sortie sortie = (Sortie) scan;
            this.scanType = "SORTIE";
            this.isEarlyExit = sortie.getIsEarly();
            if(sortie.getAssociated_entry() != null){
                this.idAssociatedEntry = sortie.getAssociated_entry().getIdScan();
                this.dateAssociatedEntry = sortie.getAssociated_entry().getDateEnregistrement();
            }
        }

    }

}
