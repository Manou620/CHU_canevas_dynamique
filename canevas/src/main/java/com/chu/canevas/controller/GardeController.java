package com.chu.canevas.controller;

import com.chu.canevas.dto.Garde.GardeDTO;
import com.chu.canevas.service.GardeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gardes")
@Validated
public class GardeController {

    @Autowired
    private GardeService gardeService;

    @PostMapping("/batch-create")
    public ResponseEntity<List<GardeDTO>> saveOrUpdateGardes (@RequestBody List<GardeDTO> gardeDTOList){
        List<GardeDTO> gardeDTOS  =gardeService.saveOrUpdateGardes(gardeDTOList);
        return new ResponseEntity<>(gardeDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GardeDTO> saveOneGarde (@RequestBody @Validated GardeDTO gardeDTO){
        GardeDTO savedGarde = gardeService.saveOneGarde(gardeDTO);
        return new ResponseEntity<>(savedGarde, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GardeDTO>> fetchGardeForOneMonth (
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam Short service_id
    ){
        List<GardeDTO> gardeDTOList = gardeService.getGardesForOneMonthForOneService(service_id, year, month);
        return new ResponseEntity<>(gardeDTOList, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneGardeEvent (@PathVariable Long id){
        gardeService.deleteOneGardeEvent(id);
        return new ResponseEntity<>("Suppression réussi", HttpStatus.OK);
    }


}
