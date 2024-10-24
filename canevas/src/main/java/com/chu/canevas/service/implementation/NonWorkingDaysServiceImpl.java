package com.chu.canevas.service.implementation;

import com.chu.canevas.model.NonWorkingDays;
import com.chu.canevas.repository.NonWorkingDaysRepository;
import com.chu.canevas.service.NonWorkingDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NonWorkingDaysServiceImpl implements NonWorkingDaysService {

    @Autowired
    private NonWorkingDaysRepository nonWorkingDaysRepository;

    @Override
    public boolean isHoliday(LocalDate date) {
        return nonWorkingDaysRepository.existsByDate(date);
    }

    @Override
    public List<NonWorkingDays> findHolidaysWithinPeriod(LocalDate startDate, LocalDate endDate) {
        return nonWorkingDaysRepository.findAllByDateBetween(startDate, endDate);
    }
}
