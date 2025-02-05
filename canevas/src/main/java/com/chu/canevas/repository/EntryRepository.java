package com.chu.canevas.repository;

import com.chu.canevas.model.Entry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntryRepository extends ScanRepository {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN false ELSE true END " +
            "FROM Entry e WHERE e.personnel.immatriculation = :personnel_IM " +
            "AND DATE(e.dateEnregistrement) = :scanDate")
    Boolean findEntryOfADateForAPersonnel(
            @Param("personnel_IM") String personnel_IM,
            @Param("scanDate") LocalDate scanDate);

    @Query("SELECT e FROM Entry e WHERE e.personnel.immatriculation = :personnel_IM ORDER BY e.dateEnregistrement DESC LIMIT 1")
    Entry findLastEntryOfAPersonnel(
            @Param("personnel_IM") String pepersonnel_IM
    );

    @Query("SELECT e FROM Entry e JOIN FETCH e.personnel p " +
            "WHERE e.answer_sortie IS NULL " +
            "AND e.idScan = ( SELECT MAX(e2.idScan) FROM Entry e2 WHERE e2.personnel = e.personnel)")
    List<Entry> findLastEntriesWithoutCorrespondingSortie();

    @Query("SELECT COUNT(e) FROM Entry e WHERE e.dateEnregistrement >= :startOfDay AND e.dateEnregistrement < :endOfDay")
    long countEntriesToday(@Param("startOfDay") Instant startOfDay, @Param("endOfDay") Instant endOfDay);

    @Query("""
        SELECT COUNT(e)
        FROM Entry e
        WHERE e.dateEnregistrement >= :startOfDay 
          AND e.dateEnregistrement < :endOfDay
          AND e.is_late = TRUE
          AND e.dateEnregistrement = (
              SELECT MIN(e2.dateEnregistrement)
              FROM Entry e2
              WHERE e2.personnel = e.personnel
                AND e2.dateEnregistrement >= :startOfDay
                AND e2.dateEnregistrement < :endOfDay
          )
    """)
    long countLateFirstEntriesToday(@Param("startOfDay") Instant startOfDay, @Param("endOfDay") Instant endOfDay);

    @Query("SELECT e FROM Entry e WHERE e.personnel.immatriculation = :personnelId ORDER BY e.dateEnregistrement DESC")
    List<Entry> findTop5ByPersonnelOrderByDateEnregistrementDesc(@Param("personnelId") String personnelId, Pageable pageable);

    @Query("SELECT e FROM Entry e ORDER BY e.dateEnregistrement DESC")
    List<Entry> findTop15OrderByDateEnregistrementDesc(Pageable pageable);

//    @Query("SELECT e FROM Entry e WHERE " +
//            "e.dateEnregistrement <= :instant " +
//            "AND " +
//            "(e.answer_sortie IS NULL " +
//            "OR " +
//            "e.answer_sortie.dateEnregistrement > :instant )")
    @Query("""
        SELECT e
        FROM Entry e
        LEFT JOIN e.answer_sortie s
        WHERE e.dateEnregistrement <= :instant
          AND (s IS NULL OR s.dateEnregistrement > :instant)
    """)
    List<Entry> findPresentAtInstant(@Param("instant") Instant instant);

    @Query("SELECT e FROM Entry e LEFT JOIN e.answer_sortie s " +
            "WHERE (e.dateEnregistrement <= :end AND (s IS NULL OR s.dateEnregistrement >= :start)) " +
            "AND (e.dateEnregistrement >= :start OR s.dateEnregistrement <= :end OR s IS NULL)")
    List<Entry> findPresentBetweenInstants(
            @Param("start") Instant start, @Param("end") Instant end
    );

}