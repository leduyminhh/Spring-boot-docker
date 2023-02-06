package com.leduyminh.userservice.service.impl;

import com.leduyminh.commons.exceptions.AuthenticationException;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.userservice.dtos.account.AccountDTO;
import com.leduyminh.userservice.dtos.account.AccountLoginDTO;
import com.leduyminh.userservice.entities.Account;
import com.leduyminh.userservice.entities.Role;
import com.leduyminh.userservice.repository.inf.AccountManagementRepo;
import com.leduyminh.userservice.repository.inf.RoleRepo;
import com.leduyminh.userservice.request.account.AccountLoginRequest;
import com.leduyminh.userservice.request.account.AccountRequest;
import com.leduyminh.userservice.service.inf.AccountManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountManagementServiceImpl implements AccountManagementService {

    @Autowired
    AccountManagementRepo accountManagementRepo;

    @Autowired
    MessageSource messageSource;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AccountLoginDTO authenticate(AccountLoginRequest request) {
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            throw new AuthenticationException("message_account_password_not_null");
        }
        return null;
    }

    @Override
    public Account createOrUpdate(AccountRequest request) {
        Account account = new Account();
        List<Long> roleIds = request.getRoleIds();
        List<Role> roles = new ArrayList<>();

//        if (request.getId() == null) {
//            String saltValue = UUID.randomUUID().toString();
//            try {
//                String hashedPassword = BusinessCommon.preparePassword(request.getPassword(), saltValue);
//                account.setPassword(hashedPassword);
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//        }

//        if (!roleIds.isEmpty()) {
//            roleIds.forEach(roleId -> {
//               Role role = roleRepo.findById(roleId).orElse(null);
//               if (role != null) {
//                   roles.add(role);
//               }
//            });
//        }

//        account = modelMapper.map(request, Account.class);
//        account.setRoles(roles);

        account = accountManagementRepo.save(account);
        return account;
    }
}
