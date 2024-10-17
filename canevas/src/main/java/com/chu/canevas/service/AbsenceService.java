package com.chu.canevas.service;

import com.chu.canevas.dto.Absence.AbsenceCreationDto;
import com.chu.canevas.dto.Absence.AbsenceDTO;

public interface AbsenceService {
    AbsenceDTO registerAbsence(AbsenceCreationDto absenceCreationDto);
}
