package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Personnel.PersonnelCreationDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.dtoMapper.PersonnelDtoMapper;
import com.chu.canevas.exception.ElementDuplicationException;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.repository.PersonnelRepository;
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

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<Personnel> personnels = personnelRepository.findAll(spec, pageable);

        return personnels.map(personnelDtoMapper);
    }



}
