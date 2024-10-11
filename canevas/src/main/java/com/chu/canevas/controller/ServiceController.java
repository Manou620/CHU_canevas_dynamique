package com.chu.canevas.controller;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

}
