package com.chu.canevas.repository;

import com.chu.canevas.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    @Query("SELECT COUNT(a) > 0 FROM Absence a WHERE a.personnel.immatriculation = :personnel_IM " +
            "AND (a.debut_absence <= :fin_date_to_check AND a.fin_absence >= :debut_date_to_check)")
    boolean existsEffectiveHolidayBetween(@Param("personnel_IM") String personnel_IM,
                                          @Param("debut_date_to_check") LocalDateTime debut_date_to_check,
                                          @Param("fin_date_to_check") LocalDateTime fin_date_to_check);

    @Query("SELECT " +
            "SUM (TIMESTAMPDIFF(DAY, a.debut_absence, a.fin_absence)) " +
            "FROM Absence a WHERE " +
            "a.personnel.immatriculation = :personnel_IM " +
            "AND a.typeAbsence.id_type_absence = :typeAbsenceId " +
            "AND a.debut_absence >= :periodStart AND a.fin_absence <= :periodEnd")
    Long sumAbsenceDurationWithinPeriod(
        @Param("personnel_IM") String personnel_IM,
        @Param("typeAbsenceId") Short typeAbsenceId,
        @Param("periodStart") LocalDateTime debut_periode,
        @Param("periodEnd") LocalDateTime fin_periode
    );

    @Query("SELECT a FROM Absence a WHERE " +
            "a.personnel.immatriculation = :personnel_IM " +
            "AND a.typeAbsence.id_type_absence = :typeAbsenceId " +
            "AND a.debut_absence >= :periodStart AND a.fin_absence <= :periodEnd" )
    List<Absence> absenceListWithinPeriod(
            @Param("personnel_IM") String personnel_IM,
            @Param("typeAbsenceId") Short typeAbsenceId,
            @Param("periodStart") LocalDateTime debut_periode,
            @Param("periodEnd") LocalDateTime fin_periode
    );

}
