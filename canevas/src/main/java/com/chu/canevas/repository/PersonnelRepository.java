package com.chu.canevas.repository;

import com.chu.canevas.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonnelRepository extends JpaRepository<Personnel, String>, JpaSpecificationExecutor<Personnel> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Personnel p WHERE p.immatriculation IN :IMs")
    void deleteByIMs(@Param("IMs") List<String> IMs);

    boolean existsByImmatriculation(String immatriculation);
}
