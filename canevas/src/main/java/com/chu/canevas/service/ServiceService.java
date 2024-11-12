package com.chu.canevas.service;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import com.chu.canevas.dto.Service.ServiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceService {

    List<ServiceDTO> getAllService();
    ServiceDTO createService(ServiceCreationDto service);

    Page<ServiceDTO> getServices(
            String id_nom_desc, int page, int size, String sortBy, String sortDirection
    );

}
