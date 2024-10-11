package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.model.Service;

import java.util.function.Function;

@org.springframework.stereotype.Service
public class ServiceDtoMapper implements Function<Service, ServiceDTO> {

    /**
     * @param service the function argument 
     * @return
     */
    @Override
    public ServiceDTO apply(Service service) {
        return new ServiceDTO(
                service.getId(),
                service.getNomService(),
                service.getDescription()
        );
    }

    public ServiceDTO EntityToServiceDTO(Service service){
        return new ServiceDTO(
                service.getId(),
                service.getNomService(),
                service.getDescription()
        );
    }
}
