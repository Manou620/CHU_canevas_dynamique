package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.dtoMapper.PersonnelDtoMapper;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
