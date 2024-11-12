package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Horaire.HoraireDTO;
import com.chu.canevas.model.Horaire;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class HoraireDtoMapper implements Function<Horaire, HoraireDTO> {
    @Override
    public HoraireDTO apply(Horaire horaire) {
        return new HoraireDTO(
            horaire.getId(),
            horaire.getLibelle_horaire(),
            horaire.getDebut_horaire(),
            horaire.getFin_horaire(),
            horaire.getFlexible()
        );
    }
}
