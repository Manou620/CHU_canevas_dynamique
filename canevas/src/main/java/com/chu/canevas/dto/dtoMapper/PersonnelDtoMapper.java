package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.model.Personnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PersonnelDtoMapper implements Function<Personnel, PersonnelDTO> {

    @Autowired
    ServiceDtoMapper serviceDtoMapper;

    /**
     * @param personnel the function argument
     * @return
     */
    @Override
    public PersonnelDTO apply(Personnel personnel) {
        return new PersonnelDTO(
                personnel.getImmatriculation(),
                personnel.getNom(),
                personnel.getFonction(),
                personnel.getPhotoPath(),
                serviceDtoMapper.EntityToServiceDTO(personnel.getService()),
                personnel.getSexe()
                //personnel.PersonnelDTO
        );
    }

    public PersonnelDTO EntityToDTO(Personnel personnel){
        return new PersonnelDTO(
                personnel.getImmatriculation(),
                personnel.getNom(),
                personnel.getFonction(),
                personnel.getPhotoPath(),
                serviceDtoMapper.EntityToServiceDTO(personnel.getService()),
                personnel.getSexe()
        );
    }
}
