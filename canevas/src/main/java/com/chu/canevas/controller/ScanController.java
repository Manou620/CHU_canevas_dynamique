package com.chu.canevas.controller;

import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.dto.RealTimeData.EntryExit;
import com.chu.canevas.dto.Scan.*;
import com.chu.canevas.service.ScanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/scan")
public class ScanController {

    @Autowired
    private ScanService scanService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/entry")
    public ResponseEntity<EntryDTO> registerEntry(@RequestBody PersonnelToScan personnel, HttpServletRequest request){
        EntryDTO savedEntry = scanService.registerEntry(personnel.getImmatriculation(), (Long) request.getAttribute("utilisateur_id"));

        List<PresentPersonnelDto> presentPersonnelDtoList = scanService.getPresentPersonnel();
        messagingTemplate.convertAndSend("/topic/present-list", presentPersonnelDtoList);
        EntryExit entryExit = scanService.getCountedEntryExitOfToday();
        messagingTemplate.convertAndSend("/topic/entry-exit-data", entryExit);
        EntryExitDTO entryExitDTO = new EntryExitDTO(savedEntry);
        messagingTemplate.convertAndSend("/topic/last-entry-exit", entryExitDTO);

        return new ResponseEntity<>(savedEntry, HttpStatus.OK); //Vo mila mijery exception
    }

    @PostMapping("/sortie")
    public ResponseEntity<SortieResponseDTO> registerSortie(@RequestBody PersonnelToScan personnel, HttpServletRequest request) throws JsonProcessingException {
        SortieResponseDTO sortieResponseDTO = scanService.registerSortie(personnel.getImmatriculation(), (Long) request.getAttribute("utilisateur_id"));

        HttpStatus status;
        if(sortieResponseDTO.getStatus() == 200){
            status = HttpStatus.OK;
        }else{
            status = HttpStatus.CREATED;
        }

        List<PresentPersonnelDto> presentPersonnelDtoList = scanService.getPresentPersonnel();
        messagingTemplate.convertAndSend("/topic/present-list", presentPersonnelDtoList);
        EntryExit entryExit = scanService.getCountedEntryExitOfToday();
        messagingTemplate.convertAndSend("/topic/entry-exit-data", entryExit);
        //EntryExitDTO entryExitDTO  =new EntryExitDTO(sortieResponseDTO);

        ObjectMapper mapper = new ObjectMapper();
        EntryExitDTO entryExitDTO = new EntryExitDTO(sortieResponseDTO);
        String json = mapper.writeValueAsString(entryExitDTO); // Test serialization

        messagingTemplate.convertAndSend("/topic/last-entry-exit", json);

        return new ResponseEntity<>(sortieResponseDTO, status);
    }



    @GetMapping("/socket-test")
    public ResponseEntity<String> socketTest() {
        List<PresentPersonnelDto> presentPersonnelDtoList = scanService.getPresentPersonnel();
        messagingTemplate.convertAndSend("/topic/present-list", presentPersonnelDtoList);
        EntryExit entryExit = scanService.getCountedEntryExitOfToday();
        messagingTemplate.convertAndSend("/topic/entry-exit-data", entryExit);
        return new ResponseEntity<>("Tested", HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<Page<ScanDTO>> getScans(
//            @RequestParam(required = false) String personnelName,
//            @RequestParam(required = false) Short id_service,
//            @RequestParam(required = false) String immatriculation,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate,
//            @RequestParam(defaultValue = "0") int page, // Default to page 0
//            @RequestParam(defaultValue = "30") int size // Default to size 10
//    ) {
//        // Construct Pageable object from page and size
//        Pageable pageable = PageRequest.of(page, size);
//
//        return ResponseEntity.ok(scanService.getFilteredScans(
//                personnelName, id_service, immatriculation, startDate, endDate, pageable));
//    }

    @GetMapping("/last-scans")
    public ResponseEntity<List<ScanDTO>> getLastScans(
            @RequestParam String immatriculation
    ) {
        List<ScanDTO> lastScans = scanService.getLast5ScansOfPersonnel(immatriculation);
        return ResponseEntity.ok(lastScans);
    }

    @GetMapping("/last-all-scans")
    public ResponseEntity<List<ScanDTO>> getLastAllScans(
    ) {
        List<ScanDTO> lastScans = scanService.getLast30Scans();
        return ResponseEntity.ok(lastScans);
    }

}
