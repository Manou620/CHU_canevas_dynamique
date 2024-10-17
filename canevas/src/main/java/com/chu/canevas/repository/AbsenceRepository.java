package com.chu.canevas.repository;

import com.chu.canevas.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    @Query("SELECT COUNT(a) > 0 FROM Absence a WHERE a.personnel.immatriculation = :personnel_IM " +
            "AND (a.debut_absence <= :fin_date_to_check AND a.fin_absence >= :fin_date_to_check)")
    boolean existsEffectiveHolidayBetween(@Param("personnel_IM") String personnel_IM,
                                          @Param("debut_date_to_check") LocalDateTime debut_date_to_check,
                                          @Param("fin_date_to_check") LocalDateTime fin_date_to_check);

}
