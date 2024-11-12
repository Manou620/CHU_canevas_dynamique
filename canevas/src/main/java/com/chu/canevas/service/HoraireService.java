package com.chu.canevas.service;

import com.chu.canevas.dto.Horaire.HoraireDTO;
import org.springframework.data.domain.Page;

import java.time.LocalTime;
import java.util.List;

public interface HoraireService {

    List<HoraireDTO> getHoraires (
         Short id_horaire, String libelle_horaire, LocalTime debut_horaire, LocalTime fin_horaire, Boolean flexible, int page, int size, String sortBy, String sortDirection
    );

    HoraireDTO getHoraireById(Short id);

}
