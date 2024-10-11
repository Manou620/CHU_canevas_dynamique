package com.chu.canevas.repository;

import com.chu.canevas.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.nom_role = ?1")
    Role findByNAme(String name);
}
