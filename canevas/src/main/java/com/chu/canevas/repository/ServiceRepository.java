package com.chu.canevas.repository;

import com.chu.canevas.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Short> {
    boolean existsByNomService(String nomService);
}
