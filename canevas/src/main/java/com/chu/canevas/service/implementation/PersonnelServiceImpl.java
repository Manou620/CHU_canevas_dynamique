package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Personnel.PersonnelCreationDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.dtoMapper.PersonnelDtoMapper;
import com.chu.canevas.exception.ElementDuplicationException;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.exception.SamePersonAsChefException;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.repository.HoraireRepository;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.repository.ServiceRepository;
import com.chu.canevas.service.PersonnelService;
import com.chu.canevas.specification.PersonnelSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelServiceImpl implements PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private HoraireRepository horaireRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PersonnelDtoMapper personnelDtoMapper;

    @Override
    public PersonnelDTO getPersonnelById(String IM){
        Personnel personnel = personnelRepository.findById(IM).orElseThrow(
                () -> new ElementNotFoundException("Personnel", IM)
        );
        return personnelDtoMapper.EntityToDTO(personnel);
    }

    @Override
    public PersonnelDTO createPersonnel(PersonnelCreationDTO personnelCreationDTO) {
        if(personnelRepository.existsById(personnelCreationDTO.IM())){
            throw new ElementDuplicationException("Ce personnel existe deja");
        }
        if(!personnelRepository.existsById(personnelCreationDTO.superieur_id())){
            throw new ElementNotFoundException("Personnel", personnelCreationDTO.superieur_id());
        }
        if(!serviceRepository.existsById(personnelCreationDTO.service_id())){
            throw new ElementNotFoundException("Ce service n'existe pas");
        }
        if(!horaireRepository.existsById(personnelCreationDTO.horaire_id())){
            throw new ElementNotFoundException("L'horaire specifié n'existe pas");
        }
        if(personnelCreationDTO.IM().equals(personnelCreationDTO.superieur_id())){
            throw new SamePersonAsChefException();
        }
        Personnel personnel = new Personnel(personnelCreationDTO);
        Personnel savedPersonnel = personnelRepository.save(personnel);
        return personnelDtoMapper.apply(savedPersonnel);
    }

    @Override
    public PersonnelDTO updatePersonnel(String IM, PersonnelCreationDTO personnelCreationDTO) {
        Optional<Personnel> optionalPersonnel = personnelRepository.findById(IM);
        if (optionalPersonnel.isPresent()) {
            Personnel personnelTosave = new Personnel(personnelCreationDTO);
            Personnel savedPersonnel = personnelRepository.save(personnelTosave);
            return personnelDtoMapper.apply(savedPersonnel);
        }else {
            throw new ElementNotFoundException("Personnel", IM);
        }
    }

    @Override
    public void deletePersonnelsByIMs(List<String> IMs) {
        personnelRepository.deleteByIMs(IMs);
    }

    @Override
    public Page<PersonnelDTO> getPersonnels(String IM_nom_function, Short id_service, String sexe, int page, int size, String sortBy, String sortDirection) {
        Specification<Personnel> spec = PersonnelSpecification.createFullSpecification(
                IM_nom_function, sexe, id_service
        );

        Sort sort  = Sort.unsorted();

        if(sortBy != null && !sortBy.isEmpty() && sortDirection != null && !sortDirection.isEmpty()){
            sort = "DESC".equalsIgnoreCase(sortDirection)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Personnel> personnels = personnelRepository.findAll(spec, pageable);

        return personnels.map(personnelDtoMapper);
    }



}
