package com.chu.canevas.repository;

import com.chu.canevas.model.NonWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NonWorkingDaysRepository extends JpaRepository<NonWorkingDays, Long> {
    boolean existsByDate(LocalDate date);
    List<NonWorkingDays> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
