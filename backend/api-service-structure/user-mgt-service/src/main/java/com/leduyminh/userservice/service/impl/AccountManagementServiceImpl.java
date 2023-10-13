package com.leduyminh.userservice.service.impl;

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
        return null;
    }

    @Override
    public Account createOrUpdate(AccountRequest request) {
        Account rs = null;
        List<Long> roleIds = request.getRoleIds();
        List<Role> roles = new ArrayList<>();

        try {
            Account account = modelMapper.map(request, Account.class);

            if (request.getId() == null) {
                // generate password + random salt value SHA-256
                try {
                    String saltValue = UUID.randomUUID().toString();
                    String hashedPassword = BusinessCommon.preparePassword(request.getPassword(), saltValue);

                    account.setPassword(hashedPassword);
                    account.setSaltValue(saltValue);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } else {
                Account oldAccount = accountManagementRepo.findById(request.getId()).get();
                // update password if change
                try {
                    String hashedUpdatePassword = BusinessCommon.preparePassword(request.getPassword(), oldAccount.getSaltValue());
                    if (!hashedUpdatePassword.equals(oldAccount.getPassword())) {
                        String saltValueNew = UUID.randomUUID().toString();
                        String hashedPassword = BusinessCommon.preparePassword(request.getPassword(), saltValueNew);

                        account.setPassword(hashedPassword);
                        account.setSaltValue(saltValueNew);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

            if (roleIds != null) {
                roleIds.forEach(roleId -> {
                    Role role = roleRepo.findById(roleId).orElse(null);
                    if (role != null) {
                        roles.add(role);
                    }
                });
            }

            account.setRoles(roles);
            rs = accountManagementRepo.save(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    @Override
    public AccountDTO findById(Long id) {
        AccountDTO result = null;
        Account account = accountManagementRepo.findById(id).orElse(null);

        if (account != null) {
            result = modelMapper.map(account, AccountDTO.class);
        }

        return result;
    }
}
