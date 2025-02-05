package com.chu.canevas.repository;

import com.chu.canevas.model.Sortie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SortieRepository extends ScanRepository {

    @Query("SELECT COUNT(s) FROM Sortie s WHERE s.dateEnregistrement >= :startOfDay AND s.dateEnregistrement < :endOfDay")
    long countExitsToday(@Param("startOfDay") Instant startOfDay, @Param("endOfDay") Instant endOfDay);

    @Query("""
    SELECT COUNT(s)
    FROM Sortie s
    WHERE s.dateEnregistrement >= :startOfDay
      AND s.dateEnregistrement < :endOfDay
      AND s.isEarly = TRUE
      AND NOT EXISTS (
          SELECT 1
          FROM Entry e
          WHERE e.personnel = s.personnel
            AND e.dateEnregistrement > s.dateEnregistrement
            AND e.dateEnregistrement < :endOfDay
      )
    """)
    long countEarlyExitsToday(@Param("startOfDay") Instant startOfDay, @Param("endOfDay") Instant endOfDay);

    @Query("SELECT s FROM Sortie s WHERE s.personnel.immatriculation = :personnelId ORDER BY s.dateEnregistrement DESC")
    List<Sortie> findTop5ByPersonnelOrderByDateEnregistrementDesc(@Param("personnelId") String personnelId, Pageable pageable);

    @Query("SELECT s FROM Sortie s ORDER BY s.dateEnregistrement DESC")
    List<Sortie> findTop15OrderByDateEnregistrementDesc(Pageable pageable);

//    @Query("""
//            SELECT s FROM Sortie s WHERE s.personnel.immatriculation = :immatriculation AND
//                s.dateEnregistrement BETWEEN :start AND :end
//            """)
//    List<Sortie> findEmployeeSortieBetweenDates( @Param("immatriculation") String immatriculation, @Param("start") Instant start, @Param("end") Instant end);

}
