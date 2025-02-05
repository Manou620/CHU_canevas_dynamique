package com.chu.canevas.repository;

import com.chu.canevas.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRepository extends JpaRepository<Service, Short>, JpaSpecificationExecutor<Service> {
    boolean existsByNomService(String nomService);
    boolean existsById(Short id);
}
