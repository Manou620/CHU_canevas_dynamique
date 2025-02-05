package com.chu.canevas.dto.Scan;

import lombok.Data;

import java.time.Instant;

@Data
public class EntryExitDTO {

    private String scan_type;
    private String scan_moment;
    private String nom_agent_scanneur;
    private String employee_name;
    private String status;

    public EntryExitDTO(SortieResponseDTO sortieResponseDTO){
        this.employee_name = sortieResponseDTO.getSortieDTO().personnel().nom();
        this.scan_moment = sortieResponseDTO.getSortieDTO().moment_enregistrement().toString();
        this.nom_agent_scanneur = sortieResponseDTO.getSortieDTO().utilisateur().nom_utilisateur();
        this.status = sortieResponseDTO.getSortieDTO().is_early() ? "tot" : "tard";
        this.scan_type = "exit";
    }

    public EntryExitDTO(EntryDTO entryDTO){
        this.employee_name = entryDTO.personnel().nom();
        this.scan_moment = entryDTO.moment_enregistrement().toString();
        this.nom_agent_scanneur = entryDTO.utilisateur().nom_utilisateur();
        this.status = entryDTO.is_late() ? "tard" : "tot";
        this.scan_type = "entry";

    }


}
