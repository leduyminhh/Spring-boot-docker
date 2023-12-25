package com.leduyminh.userservice.service.inf;

import com.leduyminh.userservice.dtos.account.AccountDTO;
import com.leduyminh.userservice.dtos.account.AccountLoginDTO;
import com.leduyminh.userservice.entities.Account;
import com.leduyminh.userservice.request.account.AccountLoginRequest;
import com.leduyminh.userservice.request.account.AccountRequest;
import org.springframework.transaction.annotation.Transactional;


public interface AccountManagementService {

    // account
    AccountLoginDTO authenticate(AccountLoginRequest request);

    // admin
    Account createOrUpdate(AccountRequest request) throws Exception;
    AccountDTO findById(Long id);
}
