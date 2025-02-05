package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Service.ServiceCreationDto;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.dto.dtoMapper.ServiceDtoMapper;
import com.chu.canevas.exception.ElementDuplicationException;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.repository.ServiceRepository;
import com.chu.canevas.service.ServiceService;
import com.chu.canevas.specification.ServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public ServiceDTO updateService(ServiceCreationDto serviceCreationDto, Short id) {
        com.chu.canevas.model.Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Service", id.toString())
        );
        service.setNomService(serviceCreationDto.nom_service());
        service.setDescription(serviceCreationDto.description());
        com.chu.canevas.model.Service savedService = serviceRepository.save(service);
        return new ServiceDTO(savedService.getId(), savedService.getNomService(), savedService.getDescription());
    }

    @Override
    public Page<ServiceDTO> getServices(String id_nom_desc, int page, int size, String sortBy, String sortDirection) {
        Specification<com.chu.canevas.model.Service> spec = ServiceSpecification.createFullSpecification(id_nom_desc);
        Sort sort = Sort.unsorted();
        if(sortBy != null && !sortBy.isEmpty() && sortDirection != null && !sortDirection.isEmpty()){
            sort = "DESC".equalsIgnoreCase(sortDirection)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<com.chu.canevas.model.Service> servicePage = serviceRepository.findAll(spec, pageable);
        return servicePage.map(serviceDtoMapper);
    }

    @Override
    public void deleteOneService(Short id) {
        serviceRepository.deleteById(id);
    }

}
