package com.chu.canevas.controller;

import com.chu.canevas.dto.Absence.AbsenceCreationDto;
import com.chu.canevas.dto.Absence.AbsenceDTO;
import com.chu.canevas.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/absence")
public class AbsenceController {

    @Autowired
    AbsenceService absenceService;

    @PostMapping
    public ResponseEntity<AbsenceDTO> registerAbsence(@RequestBody AbsenceCreationDto absenceCreationDto){
        return new ResponseEntity<>(absenceService.registerAbsence(absenceCreationDto), HttpStatus.OK);
    }

}
