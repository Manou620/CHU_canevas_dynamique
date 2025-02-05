package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Planning.PlanningCreationDto;
import com.chu.canevas.dto.Planning.PlanningDTO;
import com.chu.canevas.dto.dtoMapper.PlanningDtoMapper;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Planning;
import com.chu.canevas.repository.PersonnelRepository;
import com.chu.canevas.repository.PlanningRepository;
import com.chu.canevas.service.PersonnelService;
import com.chu.canevas.service.PlanningService;
import com.chu.canevas.specification.PlanningSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlanningServiceImpl implements PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PlanningDtoMapper planningDtoMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public Page<PlanningDTO> getPlannings(String IM_nom, Short id_service, LocalDateTime start, LocalDateTime end, Integer size, Integer page) {
        if(id_service == null){
            throw new IllegalArgumentException("Le service est obligatoire");
        }
        Pageable pageable = PageRequest.of(
                page != null ? page : DEFAULT_PAGE,
                size != null ? size : DEFAULT_PAGE_SIZE
        );

        Specification<Planning> spec = PlanningSpecification.createFullSpecification(IM_nom, id_service, start, end);

        return planningRepository.findAll(spec, pageable).map(PlanningDTO::new);
    }

    @Override
    public PlanningDTO createPlanning(PlanningCreationDto planningCreationDto) {
        boolean hasOverlap = planningRepository.existsOverlappingPlanning(planningCreationDto.immatriculation(), planningCreationDto.debut_heure(), planningCreationDto.fin_heure());

        if(hasOverlap){
            throw new IllegalArgumentException("Les plannings se chevauchent pour l'employé : " + planningCreationDto.immatriculation());
        }

        Personnel personnel = personnelRepository.findById(planningCreationDto.immatriculation()).orElseThrow(
                () -> new ElementNotFoundException("Employé", planningCreationDto.immatriculation())
        );

        Planning planning = new Planning(planningCreationDto);
        planning.setPersonnel(personnel);
        planning.setHoraire(personnel.getHoraire());
        Planning savedPlanning = planningRepository.save(planning);
        return new PlanningDTO(savedPlanning);
    }

    @Override
    public List<PlanningDTO> getNextPlaningOfAnEmployee(String immatriculation, int size) {
        Pageable pageable = PageRequest.of(0, size);
        LocalDateTime now = LocalDateTime.now();
        return planningRepository.findNextPlanningsByPersonnel(immatriculation, now, pageable).stream().map(planningDtoMapper).toList();
    }
}
