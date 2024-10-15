package com.chu.canevas.controller;

import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.PersonnelToScan;
import com.chu.canevas.dto.Scan.SortieDTO;
import com.chu.canevas.service.ScanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scan")
public class ScanController {

    @Autowired
    private ScanService scanService;

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
        return null;
    }

}
