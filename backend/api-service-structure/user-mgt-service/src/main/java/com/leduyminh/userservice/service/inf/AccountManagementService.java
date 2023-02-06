package com.leduyminh.userservice.service.inf;

import com.leduyminh.userservice.dtos.account.AccountLoginDTO;
import com.leduyminh.userservice.entities.Account;
import com.leduyminh.userservice.request.account.AccountLoginRequest;
import com.leduyminh.userservice.request.account.AccountRequest;

public interface AccountManagementService {
    AccountLoginDTO authenticate(AccountLoginRequest request);
    Account createOrUpdate(AccountRequest request);
}
