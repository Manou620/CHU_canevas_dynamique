package com.chu.canevas.dto.Scan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScanDTO {
    private Long idScan;
    private String scanType; // "ENTRY" or "EXIT"
    private String observation;
    private String personnelName; // Combine personnel first and last name
    private String immatriculation;
    private Boolean isLateEntry;
    private Boolean isEarlyExit;
    private Boolean isFirstEntry;
    private String service; // Service of personnel
    private String AgentName;
    private Instant dateEntregistrement;
}

