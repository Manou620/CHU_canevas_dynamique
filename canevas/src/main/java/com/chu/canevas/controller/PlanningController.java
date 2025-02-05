package com.chu.canevas.controller;

import com.chu.canevas.dto.Planning.PlanningCreationDto;
import com.chu.canevas.dto.Planning.PlanningDTO;
import com.chu.canevas.repository.PlanningRepository;
import com.chu.canevas.service.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/plannings")
@Validated
public class PlanningController {

    @Autowired
    PlanningService planningService;

    @GetMapping
    public ResponseEntity<Page<PlanningDTO>> getPlannings(
            @RequestParam(required = false) String im_nom,
            @RequestParam Short id_service,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        return new ResponseEntity<>(planningService.getPlannings(im_nom, id_service, debut, fin, size, page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlanningDTO> createPlanning (@Validated @RequestBody PlanningCreationDto planningCreationDto){
        return new ResponseEntity<>(planningService.createPlanning(planningCreationDto), HttpStatus.OK);
    };

    @GetMapping("/next")
    public ResponseEntity<List<PlanningDTO>> getNextPlannings(@RequestParam String immatriculation, @RequestParam int size) {
        List<PlanningDTO> plannings = planningService.getNextPlaningOfAnEmployee(immatriculation, size);
        return ResponseEntity.ok(plannings);
    }



}
