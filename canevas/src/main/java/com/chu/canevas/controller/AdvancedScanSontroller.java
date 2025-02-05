package com.chu.canevas.controller;

import com.chu.canevas.dto.Presence.PresenceDTO;
import com.chu.canevas.dto.Scan.AdvancedScanDTO;
import com.chu.canevas.service.AdvancedScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/advanced-scan")
public class AdvancedScanSontroller {

    @Autowired
    private AdvancedScanService advancedScanService;

    @GetMapping
    public ResponseEntity<Page<AdvancedScanDTO>> getScans (
            @RequestParam(required = false) Instant startDateRange,
            @RequestParam(required = false) Instant endDateRange,
            @RequestParam(required = false) Short service_id,
            @RequestParam(required = false) String immatriculation,
            @RequestParam(required = false) Long userScannerId,
            @RequestParam(required = false) String scanType,
            @RequestParam(defaultValue = "dateEnregistrement") String sortBy,
            @RequestParam (defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    )
    {
        Page<AdvancedScanDTO> advancedScanDTOPage = advancedScanService.getAdvancedScans(
                startDateRange, endDateRange, service_id, immatriculation,
                userScannerId, scanType,
                size, page, sortBy, sortDirection
        );

        return new ResponseEntity<>(advancedScanDTOPage, HttpStatus.OK);
    }

    @GetMapping("/presence-at")
    public List<PresenceDTO> getPresenceAtInstant(@RequestParam Instant instant){
        return advancedScanService.getPresenceAtInstant(instant);
    }

    @GetMapping("presence-between")
    public List<PresenceDTO> getPresenceBetweenInstants (
            @RequestParam Instant start,
            @RequestParam Instant end
    ){
        return advancedScanService.getPresenceBetweenInstants(start, end);
    }

}
