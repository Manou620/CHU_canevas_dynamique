package com.chu.canevas.repository;

import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Scan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
@Primary
@Qualifier("scanRepository")
public interface ScanRepository extends CrudRepository<Scan, Long>, JpaSpecificationExecutor<Scan> {

    @Query(value = "SELECT s FROM Scan s WHERE s.personnel = :personnel ORDER BY s.dateEnregistrement DESC LIMIT 1")
    Scan findTopByPersonnelOrderByDateEnregistrementDesc(@Param("personnel") Personnel personnel);


}
