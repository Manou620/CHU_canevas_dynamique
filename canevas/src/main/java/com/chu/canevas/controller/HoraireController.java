package com.chu.canevas.controller;

import com.chu.canevas.dto.Horaire.HoraireDTO;
import com.chu.canevas.service.HoraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/horaires")
public class HoraireController {

    @Autowired
    private HoraireService horaireService;

    @GetMapping
    public ResponseEntity<List<HoraireDTO>> getHoraires (
            @RequestParam(required = false) Short id_horaire,
            @RequestParam(required = false) String libelle_horaire,
            @RequestParam(required = false) LocalTime debut_horaire,
            @RequestParam(required = false) LocalTime fin_horaire,
            @RequestParam(required = false) Boolean flexible,
            @RequestParam String sortBy,
            @RequestParam (defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<HoraireDTO> horaireDTOS = horaireService.getHoraires(
                id_horaire, libelle_horaire, debut_horaire, fin_horaire, flexible, page, size, sortBy, sortDirection
        );
        return new ResponseEntity<>(horaireDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoraireDTO> getHoraireById (
            @PathVariable Short id
    ){
        return new ResponseEntity<>(horaireService.getHoraireById(id), HttpStatus.OK);
    }

}
