package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.dto.dtoMapper.ServiceDtoMapper;
import com.chu.canevas.exception.ElementDuplicationException;
import com.chu.canevas.repository.ServiceRepository;
import com.chu.canevas.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceDtoMapper serviceDtoMapper;

    /**
     * @return
     */
    @Override
    public List<ServiceDTO> getAllService() {
        return serviceRepository.findAll()
                .stream()
                .map(serviceDtoMapper)
                .collect(Collectors.toList());
    }

    /**
     * @param service
     * @return  Service DTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // rollback tonga de miaraka @ SQLException sy IOException
    public ServiceDTO createService(ServiceCreationDto serviceCreationDto) {
        if(serviceRepository.existsByNomService(serviceCreationDto.nom_service())){
            throw new ElementDuplicationException("Ce service existe déja");
        }
        com.chu.canevas.model.Service service = new com.chu.canevas.model.Service(serviceCreationDto);
        com.chu.canevas.model.Service savedService = serviceRepository.save(service);
        return serviceDtoMapper.EntityToServiceDTO(savedService);
    }

}
