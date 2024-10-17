package com.chu.canevas.dto.Absence;

import com.chu.canevas.dto.TypeAbsence.TypeAbsenceDto;
import com.chu.canevas.model.Personnel;

import java.time.LocalDateTime;

public record AbsenceCreationDto (

        LocalDateTime debut,
        LocalDateTime fin,
        String motif,
        Short type_id,
        Boolean isExceptionalCase,
        String personnel_IM,
        String interim_IM
){
}
