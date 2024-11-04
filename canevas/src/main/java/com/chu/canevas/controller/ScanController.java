package com.chu.canevas.controller;

import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.PersonnelToScan;
import com.chu.canevas.dto.Scan.SortieDTO;
import com.chu.canevas.service.ScanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/scan")
public class ScanController {

    @Autowired
    private ScanService scanService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

//    @PostMapping("/entry")
//    public ResponseEntity<EntryDTO> registerEntry(@RequestBody ScanId id, HttpServletRequest request){
//        //return new ResponseEntity<>(request.getAttribute("utilisateur_id"), HttpStatus.OK);
//        return new ResponseEntity<>(scanService.registerEntry(), HttpStatus.OK);
//    }
    @PostMapping("/entry")
    public ResponseEntity<EntryDTO> registerEntry(@RequestBody PersonnelToScan personnel, HttpServletRequest request){
        //return new ResponseEntity<>(request.getAttribute("utilisateur_id"), HttpStatus.OK);
        return new ResponseEntity<>(scanService.registerEntry(personnel.getImmatriculation(), (Long) request.getAttribute("utilisateur_id")), HttpStatus.OK); //Vo mila mijery exception
    }

    @PostMapping("/sortie")
    public ResponseEntity<SortieDTO> registerSortie(@RequestBody PersonnelToScan personnel, HttpServletRequest request){
        return new ResponseEntity<>(scanService.registerSortie(personnel.getImmatriculation(), (Long) request.getAttribute("utilisateur_id")), HttpStatus.OK);
    }

    @GetMapping("/socket-test")
    public ResponseEntity<String> socketTest() {
        List<PresentPersonnelDto> presentPersonnelDtoList = scanService.getPresentPersonnel();
        messagingTemplate.convertAndSend("/topic/present-list", presentPersonnelDtoList);
        return new ResponseEntity<>("Tested", HttpStatus.OK);
    }

}
