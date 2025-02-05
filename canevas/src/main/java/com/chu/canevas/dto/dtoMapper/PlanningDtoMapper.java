package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Planning.PlanningDTO;
import com.chu.canevas.model.Planning;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlanningDtoMapper implements Function<Planning, PlanningDTO> {

    @Override
    public PlanningDTO apply(Planning planning) {
        return new PlanningDTO(
                planning.getId_planning(),
                planning.getDebut_heure().toString(),
                planning.getFin_heure().toString(),
                planning.getPersonnel().getNom(),
                planning.getPersonnel().getImmatriculation(),
                planning.getPersonnel().getService().getNomService(),
                planning.getPersonnel().getFonction(),
                planning.getPersonnel().getSexe()
        );
    }
}
