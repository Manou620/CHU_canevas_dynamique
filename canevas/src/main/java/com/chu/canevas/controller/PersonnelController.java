package com.chu.canevas.controller;

import com.chu.canevas.dto.Personnel.PersonnelCreationDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/personnel")
@Validated
public class PersonnelController {

    @Autowired
    PersonnelService personnelService;

    @GetMapping("/{im}")
    public ResponseEntity<PersonnelDTO> getPersonnelByIM(@PathVariable String im){
        return new ResponseEntity<PersonnelDTO>(personnelService.getPersonnelById(im), HttpStatus.OK);
    }

    @DeleteMapping("/multiple-delete")
    public  ResponseEntity<String> deleteMultiplePersonnel(@RequestBody List<String> IMs){
        System.out.println(IMs);
        personnelService.deletePersonnelsByIMs(IMs);
        return new ResponseEntity<>("Suppression reussi", HttpStatus.OK);
    }

    @GetMapping("/get-filter")
    public ResponseEntity<Page<PersonnelDTO>> getSpecifiedPersonnel (
            @RequestParam(required = false) String IM_nom_function,
            @RequestParam(required = false) String sexe,
            @RequestParam(required = false) Short id_service,
            @RequestParam(defaultValue = "immatriculation") String sortBy,
            @RequestParam (defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<PersonnelDTO> personnelDTOS = personnelService.getPersonnels(
                IM_nom_function, id_service, sexe, page, size, sortBy, sortDirection
        );
        return new ResponseEntity<>(personnelDTOS, HttpStatus.OK);
    }

    @PutMapping("/{IM}")
    public ResponseEntity<PersonnelDTO> updatePersonnel (@PathVariable String IM, @Validated @RequestBody PersonnelCreationDTO personnelCreationDTO) {
        return new ResponseEntity<>(personnelService.updatePersonnel(IM, personnelCreationDTO), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PersonnelDTO> createPersonnel (@Validated @RequestBody PersonnelCreationDTO personnelCreationDTO) {
        return new ResponseEntity<>(personnelService.createPersonnel(personnelCreationDTO), HttpStatus.OK);
    }

}
