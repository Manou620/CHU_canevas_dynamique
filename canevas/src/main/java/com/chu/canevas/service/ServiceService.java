package com.chu.canevas.service;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import com.chu.canevas.dto.Service.ServiceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceService {

    List<ServiceDTO> getAllService();
    ServiceDTO createService(ServiceCreationDto service);

}
