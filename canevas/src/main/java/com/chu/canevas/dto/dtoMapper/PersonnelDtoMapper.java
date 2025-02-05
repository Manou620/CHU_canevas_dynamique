package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Horaire.HoraireDTO;
import com.chu.canevas.dto.Personnel.HeavyPersonnelDto;
import com.chu.canevas.dto.Personnel.PersonnelCreationDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.model.Horaire;
import com.chu.canevas.model.Personnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PersonnelDtoMapper implements Function<Personnel, PersonnelDTO> {

    @Autowired
    ServiceDtoMapper serviceDtoMapper;

    @Autowired
    HoraireDtoMapper horaireDtoMapper;

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

    public HeavyPersonnelDto entityToHeavyPersonnelDTO (Personnel personnel){

        PersonnelDTO superieur = null;
        if(personnel.getSuperieur() != null){
            ServiceDTO sup_service = createServiceDto(personnel.getSuperieur().getService());
            superieur = new PersonnelDTO(personnel.getSuperieur().getImmatriculation(), personnel.getSuperieur().getNom(), personnel.getSuperieur().getFonction(), personnel.getSuperieur().getPhotoPath(), sup_service, personnel.getSuperieur().getSexe());
        }

        HoraireDTO pers_horaire = createHoraireDTO(personnel.getHoraire());
        ServiceDTO pers_service = serviceDtoMapper.apply(personnel.getService());
        return new HeavyPersonnelDto(
            personnel.getImmatriculation(),
            personnel.getNom(),
            personnel.getFonction(),
            personnel.getPhotoPath(),
            personnel.getSexe(),
            superieur,
            pers_horaire,
            pers_service
        );
    }

    private ServiceDTO createServiceDto(com.chu.canevas.model.Service service){
        return new ServiceDTO(
          service.getId(),
          service.getNomService(),
          service.getDescription()
        );
    }

    private HoraireDTO  createHoraireDTO (Horaire horaire){
        return horaireDtoMapper.apply(horaire);
    }
}
