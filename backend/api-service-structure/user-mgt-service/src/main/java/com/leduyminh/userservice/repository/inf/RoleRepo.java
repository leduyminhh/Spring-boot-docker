package com.leduyminh.userservice.repository.inf;

import com.leduyminh.userservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
