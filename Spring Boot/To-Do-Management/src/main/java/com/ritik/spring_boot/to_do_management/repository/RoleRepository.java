package com.ritik.spring_boot.to_do_management.repository;

import com.ritik.spring_boot.to_do_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
