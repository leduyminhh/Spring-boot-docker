package com.leduyminh.userservice.service.inf;

import com.leduyminh.userservice.entities.Role;
import com.leduyminh.userservice.request.role.RoleRequest;

public interface RoleService {
    Role createOrUpdate(RoleRequest request);
}
