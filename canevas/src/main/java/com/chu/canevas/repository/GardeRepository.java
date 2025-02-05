package com.chu.canevas.repository;

import com.chu.canevas.model.Garde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GardeRepository extends JpaRepository<Garde, Long> {

    @Query("SELECT g FROM Garde g WHERE g.service.id = :serviceId AND YEAR(g.date) = :year AND MONTH(g.date) = :month")
    List<Garde> findGardeByServiceAndMonth (
            @Param("serviceId") Short service_id,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT g FROM Garde g WHERE g.personnel.immatriculation = :immatriculation AND g.date = :today")
    Garde findFirstByPersonnelAndDate(@Param("immatriculation") String immatriculation, @Param("today") LocalDate today);

}
