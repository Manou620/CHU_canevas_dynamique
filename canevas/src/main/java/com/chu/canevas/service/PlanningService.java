package com.chu.canevas.service;

import com.chu.canevas.dto.Planning.PlanningCreationDto;
import com.chu.canevas.dto.Planning.PlanningDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;


public interface PlanningService {
    Page<PlanningDTO> getPlannings (
            String IM_nom, Short id_service,
            LocalDateTime start, LocalDateTime end,
            Integer size, Integer page
    );
    PlanningDTO createPlanning (PlanningCreationDto planningCreationDto);

    List<PlanningDTO> getNextPlaningOfAnEmployee (String immatriculation, int size) ;



}
