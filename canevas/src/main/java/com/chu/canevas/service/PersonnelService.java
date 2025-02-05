package com.chu.canevas.service;

import com.chu.canevas.dto.Personnel.HeavyPersonnelDto;
import com.chu.canevas.dto.Personnel.PersonnelCreationDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PersonnelService {
    PersonnelDTO getPersonnelById(String IM);

    HeavyPersonnelDto getHeavyPersonnelDetails(String IM);

    PersonnelDTO createPersonnel(PersonnelCreationDTO personnelCreationDTO);
    HeavyPersonnelDto updatePersonnel(String IM, PersonnelCreationDTO personnelCreationDTO);

    void deletePersonnelsByIMs(List<String> IMs);

    Page<PersonnelDTO> getPersonnels (
            String IM_nom_function, Short id_service, String sexe, int page, int size, String sortBy, String sortDirection
    );

    //generer QRCode
    //Uploader, supprimer image
}
