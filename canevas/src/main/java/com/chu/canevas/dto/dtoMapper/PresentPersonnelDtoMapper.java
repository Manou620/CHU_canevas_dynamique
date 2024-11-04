package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.model.Entry;
import com.chu.canevas.model.Personnel;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PresentPersonnelDtoMapper implements Function<Entry, PresentPersonnelDto> {
    @Override
    public PresentPersonnelDto apply(Entry entry) {

        Personnel personnel = new Personnel(entry.getPersonnel());

        return new PresentPersonnelDto(
                personnel.getImmatriculation(),
                personnel.getNom(),
                personnel.getService().getNomService(),
                entry.getDate_enregistrement(),
                entry.getFirst_entry(),
                entry.getUtilisateur().getNom_utilisateur()
        );
    }
}
