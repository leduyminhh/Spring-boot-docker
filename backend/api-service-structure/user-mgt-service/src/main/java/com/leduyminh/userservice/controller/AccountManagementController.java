package com.leduyminh.userservice.controller;

import com.leduyminh.commons.dtos.DataResponse;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.userservice.dtos.account.AccountDTO;
import com.leduyminh.userservice.entities.Account;
import com.leduyminh.userservice.request.account.AccountLoginRequest;
import com.leduyminh.userservice.request.account.AccountRequest;
import com.leduyminh.userservice.service.inf.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/accounts")
public class AccountManagementController extends BaseController{

    @Autowired
    AccountManagementService accountManagementService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<DataResponse> doAuthenticate(@RequestBody AccountLoginRequest request) {
        return BusinessCommon.createResponse(null, super.getMessage("message_action_success_commons"), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<DataResponse> createOrUpdate(@Valid @RequestBody AccountRequest request) {
        Account result = accountManagementService.createOrUpdate(request);
        return BusinessCommon.createResponse(result, super.getMessage("message_action_success_commons"), HttpStatus.OK);
    }
}
