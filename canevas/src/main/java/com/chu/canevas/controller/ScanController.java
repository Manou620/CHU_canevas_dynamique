package com.chu.canevas.controller;

import com.chu.canevas.dto.Scan.ScanId;
import com.chu.canevas.repository.EntryRepository;
import com.chu.canevas.repository.SortieRepository;
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

    @PostMapping("/entry")
    public ResponseEntity<?> registerEntry(@RequestBody ScanId id, HttpServletRequest request){
        return new ResponseEntity<>(request.getAttribute("utilisateur_id"), HttpStatus.OK);
    }

}
