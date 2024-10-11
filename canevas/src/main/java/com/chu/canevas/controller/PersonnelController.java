package com.chu.canevas.controller;

import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personnel")
@Validated
public class PersonnelController {

    @Autowired
    PersonnelService personnelService;

    @GetMapping("/{im}")
    public ResponseEntity<PersonnelDTO> getPersonnelByIM(@PathVariable String im){
        return new ResponseEntity<PersonnelDTO>(personnelService.getPersonnelById(im), HttpStatus.OK);
    }

}
