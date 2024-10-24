package com.chu.canevas.service;

import com.chu.canevas.model.NonWorkingDays;

import java.time.LocalDate;
import java.util.List;

public interface NonWorkingDaysService {
    boolean isHoliday(LocalDate date);
    public List<NonWorkingDays> findHolidaysWithinPeriod(LocalDate startDate, LocalDate endDate);
}
