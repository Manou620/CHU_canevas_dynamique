package com.chu.canevas.repository;

import com.chu.canevas.model.Entry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntryRepository extends ScanRepository{

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN false ELSE true END " +
            "FROM Entry e WHERE e.personnel.immatriculation = :personnel_IM " +
            "AND DATE(e.date_enregistrement) = :scanDate")
    Boolean findEntryOfADateForAPersonnel(
                @Param("personnel_IM") String personnel_IM,
                @Param("scanDate") LocalDate scanDate);

    @Query("SELECT e FROM Entry e WHERE e.personnel.immatriculation = :personnel_IM ORDER BY e.date_enregistrement DESC LIMIT 1")
    Entry findLastEntryOfAPersonnel(
            @Param("personnel_IM") String pepersonnel_IM
    );

    @Query("SELECT e FROM Entry e JOIN FETCH e.personnel p " +
            "WHERE e.answer_sortie IS NULL " +
            "AND e.id_scan = ( SELECT MAX(e2.id_scan) FROM Entry e2 WHERE e2.personnel = e.personnel)")
    List<Entry> findLastEntriesWithoutCorrespondingSortie();
}
