package com.chu.canevas.repository;

import com.chu.canevas.model.Entry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EntryRepository extends ScanRepository{

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN false ELSE true END " +
            "FROM Entry e WHERE e.personnel.immatriculation = :personnel_IM " +
            "AND DATE(e.date_enregistrement) = :scanDate")
    Boolean findEntryOfADateForAPersonnel(
                @Param("personnel_IM") String personnel_IM,
                @Param("scanDate") LocalDate scanDate);

}
