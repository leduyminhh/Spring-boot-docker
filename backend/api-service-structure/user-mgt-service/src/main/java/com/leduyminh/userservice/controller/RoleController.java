package com.leduyminh.userservice.controller;

import com.leduyminh.commons.dtos.DataResponse;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.userservice.entities.Role;
import com.leduyminh.userservice.request.role.RoleRequest;
import com.leduyminh.userservice.service.inf.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/roles")
public class RoleController extends BaseController{

    @Autowired
    RoleService roleService;

    @PostMapping(value = "")
    public ResponseEntity<DataResponse> createOrUpdate(@RequestBody RoleRequest request) {
        Role result = roleService.createOrUpdate(request);
        return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
    }
}
