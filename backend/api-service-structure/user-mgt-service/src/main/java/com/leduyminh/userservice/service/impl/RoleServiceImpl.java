package com.leduyminh.userservice.service.impl;

import com.leduyminh.commons.enums.StatusEnum;
import com.leduyminh.userservice.entities.Role;
import com.leduyminh.userservice.repository.inf.RoleRepo;
import com.leduyminh.userservice.request.role.RoleRequest;
import com.leduyminh.userservice.service.inf.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public Role createOrUpdate(RoleRequest request) {
        Role role;
        role = modelMapper.map(request, Role.class);

        if (request.getId() == null) {
            role.setActive(StatusEnum.ACTIVE.getValue());
            role.setDeleted(StatusEnum.DELETED.getValue());
        }

        roleRepo.save(role);
        return role;
    }
}
