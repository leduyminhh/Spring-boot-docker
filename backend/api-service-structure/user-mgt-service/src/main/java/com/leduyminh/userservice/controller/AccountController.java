package com.leduyminh.userservice.controller;

import com.leduyminh.commons.dtos.DataResponse;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.userservice.dtos.account.AccountLoginDTO;
import com.leduyminh.userservice.request.account.AccountLoginRequest;
import com.leduyminh.userservice.service.inf.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController extends BaseController{

    @Autowired
    AccountManagementService accountManagementService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<DataResponse> doAuthenticate(@RequestBody AccountLoginRequest request) {
        AccountLoginDTO result = accountManagementService.authenticate(request);
        return BusinessCommon.createResponse(result, super.getMessage("message.action.success.commons"), HttpStatus.OK);
    }
}
