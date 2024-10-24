package com.chu.canevas.dto.dtoMapper;

import com.chu.canevas.dto.Absence.AbsenceDTO;
import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.Service.ServiceDTO;
import com.chu.canevas.dto.TypeAbsence.TypeAbsenceDto;
import com.chu.canevas.model.Absence;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AbsenceDtoMapper implements Function<Absence, AbsenceDTO> {



    @Override
    public AbsenceDTO apply(Absence absence) {

        TypeAbsenceDto typeAbsenceDto = new TypeAbsenceDto(
            absence.getTypeAbsence().getId_type_absence(),
            absence.getTypeAbsence().getDuree_max_par_prise(),
            absence.getTypeAbsence().getDuree_max_cumul_autorise(),
            absence.getTypeAbsence().getFrequence().toString(),
            absence.getTypeAbsence().getNomTypeConge()
        );

        PersonnelDTO personnel = new PersonnelDTO(
            absence.getPersonnel().getImmatriculation(),
            absence.getPersonnel().getNom(),
            absence.getPersonnel().getFonction(),
            absence.getPersonnel().getPhotoPath(),
            new ServiceDTO(
                    absence.getPersonnel().getService().getId(),
                    absence.getPersonnel().getService().getNomService(),
                    absence.getPersonnel().getService().getDescription()
            )
        );

        PersonnelDTO interim = new PersonnelDTO(
                absence.getInterim().getImmatriculation(),
                absence.getInterim().getNom(),
                absence.getInterim().getFonction(),
                absence.getInterim().getPhotoPath(),
                new ServiceDTO(
                        absence.getInterim().getService().getId(),
                        absence.getInterim().getService().getNomService(),
                        absence.getInterim().getService().getDescription()
                )
        );

        return new AbsenceDTO(
             absence.getId_absence(),
             absence.getDebut_absence(),
             absence.getFin_absence(),
             absence.getMotif(),
             typeAbsenceDto,
             personnel,
             interim
        );
    }
}
