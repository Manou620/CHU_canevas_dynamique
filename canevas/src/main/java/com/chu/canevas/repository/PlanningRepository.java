package com.chu.canevas.repository;

import com.chu.canevas.model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PlanningRepository extends JpaRepository<Planning, Long> {

    @Query("SELECT p FROM Planning p WHERE p.personnel.immatriculation = :personnel_IM AND p.date_prevu = :currentDate AND :currentDateTime BETWEEN p.debut_heure AND p.fin_heure")
    Planning getCurrentPlanningOfAPersonnel(
            @Param("personnel_IM") String personnel_IM,
            @Param("currentDate") LocalDate date_prevu,
            @Param("currentDateTime") LocalDateTime heure_actuel
    );

}
