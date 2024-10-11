package com.chu.canevas.repository;

import com.chu.canevas.model.Scan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface ScanRepository extends CrudRepository<Scan, Long> {

    @Query("SELECT s FROM Scan s WHERE s.personnel = :personnel_IM ORDER BY s.date_enregistrement DESC")
    Scan findLastScanMade(@Param("personnel_IM") String personel_IM);

}
