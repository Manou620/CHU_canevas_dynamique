package com.chu.canevas.dto.Scan;

import com.chu.canevas.model.Scan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


//@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class IncompatibleScanResponse {

    private String message;
    private Long lastScan_id;
    private Instant lastScan_id_date;
//    private Long registeredScan_id;
//    private Instant registeredScan_id_date;

    public IncompatibleScanResponse(String message, Long lastScan_id, Instant lastScan_id_date ){
        this.message = message;
        this.lastScan_id = lastScan_id;
        this.lastScan_id_date = lastScan_id_date;
    }

}
