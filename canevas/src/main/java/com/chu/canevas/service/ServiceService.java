package com.chu.canevas.service;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import com.chu.canevas.dto.Service.ServiceDTO;
import jdk.jfr.Threshold;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceService {

    List<ServiceDTO> getAllService();
    ServiceDTO createService(ServiceCreationDto service);
    ServiceDTO updateService(ServiceCreationDto serviceCreationDto, Short id);
    Page<ServiceDTO> getServices(
            String id_nom_desc, int page, int size, String sortBy, String sortDirection
    );
    void deleteOneService(Short id);

}
