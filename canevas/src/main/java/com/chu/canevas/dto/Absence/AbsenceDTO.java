package com.chu.canevas.dto.Absence;

import com.chu.canevas.dto.Personnel.PersonnelDTO;
import com.chu.canevas.dto.TypeAbsence.TypeAbsenceDto;

import java.time.LocalDateTime;

public record AbsenceDTO(
        Long id,
        LocalDateTime debut_absence,
        LocalDateTime fin_absence,
        String motif,
        TypeAbsenceDto type,
        PersonnelDTO personnel,
        PersonnelDTO interim
) {
}
