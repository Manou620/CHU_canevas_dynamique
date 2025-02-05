package com.chu.canevas.repository;

import com.chu.canevas.model.Planning;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Long>, JpaSpecificationExecutor<Planning> {

    @Query("SELECT p FROM Planning p WHERE p.personnel.immatriculation = :personnel_IM AND p.date_prevu = :currentDate AND :currentDateTime BETWEEN p.debut_heure AND p.fin_heure")
    Planning getCurrentPlanningOfAPersonnel(
            @Param("personnel_IM") String personnel_IM,
            @Param("currentDate") LocalDate date_prevu,
            @Param("currentDateTime") LocalDateTime heure_actuel
    );

    @Query("SELECT COUNT(p) > 0 FROM Planning p " +
            "WHERE p.personnel.immatriculation = :immatriculation " +
            "AND ((:debut BETWEEN p.debut_heure AND p.fin_heure) " +
            "OR (:fin BETWEEN p.debut_heure AND p.fin_heure) " +
            "OR (p.debut_heure BETWEEN :debut AND :fin) " +
            "OR (p.fin_heure BETWEEN :debut AND :fin))")
    boolean existsOverlappingPlanning( @Param("immatriculation") String immatriculation,
                                       @Param("debut") LocalDateTime debut,
                                       @Param("fin") LocalDateTime fin );

    @Query("SELECT p FROM Planning p WHERE p.personnel.immatriculation = :immatriculation " +
            "AND p.debut_heure > :currentDateTime ORDER BY p.debut_heure ASC")
    List<Planning> findNextPlanningsByPersonnel(@Param("immatriculation") String immatriculation,
                                                @Param("currentDateTime") LocalDateTime currentDateTime,
                                                Pageable pageable);

}
