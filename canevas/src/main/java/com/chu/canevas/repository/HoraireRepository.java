package com.chu.canevas.repository;

import com.chu.canevas.model.Horaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HoraireRepository extends JpaRepository<Horaire, Short>, JpaSpecificationExecutor<Horaire> {
}
