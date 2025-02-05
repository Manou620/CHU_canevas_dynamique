package com.chu.canevas.controller;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/service")
@Validated
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

//    public ServiceDTO createService (@RequestBody ServiceCreationDto service){
//
//    }

    @GetMapping
    public List<ServiceDTO> getAllService(){
        return serviceService.getAllService();
    }

    @PostMapping
    public ResponseEntity<ServiceDTO> registerService(@Valid @RequestBody ServiceCreationDto service){
        return new ResponseEntity<>(serviceService.createService(service), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(@Valid @RequestBody ServiceCreationDto serviceCreationDto, @PathVariable Short id){
        return new ResponseEntity<>(serviceService.updateService(serviceCreationDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Short id){
        serviceService.deleteOneService(id);
        return new ResponseEntity<>("Suppression de service réussi", HttpStatus.OK);
    }

    @GetMapping("/filtered")
    public ResponseEntity<Page<ServiceDTO>> getServices (
            @RequestParam(required = false) String id_nom_desc,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam (defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<ServiceDTO> serviceDTOS = serviceService.getServices(
                id_nom_desc, page, size, sortBy, sortDirection
        );
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }



}
