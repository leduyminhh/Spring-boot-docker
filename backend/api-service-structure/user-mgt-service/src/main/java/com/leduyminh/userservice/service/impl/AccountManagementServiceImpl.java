package com.leduyminh.userservice.service.impl;

import com.leduyminh.commons.enums.AuthProvider;
import com.leduyminh.commons.exceptions.AuthenticationException;
import com.leduyminh.commons.utils.BusinessCommon;
import com.leduyminh.commons.utils.JwtTokenUtils;
import com.leduyminh.commons.utils.StringUtils;
import com.leduyminh.userservice.dtos.account.AccountDTO;
import com.leduyminh.userservice.dtos.account.AccountLoginDTO;
import com.leduyminh.userservice.entities.Account;
import com.leduyminh.userservice.entities.Role;
import com.leduyminh.userservice.repository.inf.AccountManagementRepo;
import com.leduyminh.userservice.repository.inf.RoleRepo;
import com.leduyminh.userservice.request.account.AccountLoginRequest;
import com.leduyminh.userservice.request.account.AccountRequest;
import com.leduyminh.userservice.service.BaseService;
import com.leduyminh.userservice.service.inf.AccountManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountManagementServiceImpl extends BaseService implements AccountManagementService {

    @Autowired
    AccountManagementRepo accountManagementRepo;

    @Autowired
    MessageSource messageSource;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public AccountLoginDTO authenticate(AccountLoginRequest request) {
        AccountLoginDTO result = new AccountLoginDTO();
        try {
            String username = request.getUsername();
            String password = request.getPassword();
            if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password)) {
                throw new AuthenticationException(super.getMessage("message.account.validate.username.not.null.and.blank"));
            }

            Account account = accountManagementRepo.findByUsernameAndDeleted(username, 0);
            if (account == null) {
                throw new AuthenticationException(super.getMessage("message.account.validate.account.not.exists"));
            }

            String preparePassword = BusinessCommon.preparePassword(password, account.getSaltValue());
            if (!account.getPassword().equals(preparePassword)) {
                throw new AuthenticationException(super.getMessage("message.account.validate.password"));
            }

            result = modelMapper.map(account, AccountLoginDTO.class);

            Map<String, Object> claims = new HashMap<>();
            List<String> roles = account.getRoles().stream().map(item -> item.getName()).collect(Collectors.toList());
            claims.put(jwtTokenUtils.AUTHORITIES_KEY, roles);
            claims.put("provider", account.getProvider());
            String token = jwtTokenUtils.doGenerateToken(claims, username);
            result.setToken(token);

        } catch (AuthenticationException aEx) {
            throw new AuthenticationException(aEx.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Account createOrUpdate(AccountRequest request) throws Exception {
        Account rs = null;
        List<Long> roleIds = request.getRoleIds();
        List<Role> roles = new ArrayList<>();

        if (roleIds != null) {
            roleIds.forEach(roleId -> {
                Role role = roleRepo.findById(roleId).orElse(null);
                if (role != null) {
                    roles.add(role);
                }
            });
        }

        try {
            Account account = modelMapper.map(request, Account.class);

            if (request.getId() == null) {
                boolean validUsername = this.validUserName(request.getUsername());
                if (!validUsername) {
                    throw new AuthenticationException(super.getMessage("message.account.validate.account.exists"));
                }

                boolean validEmail = this.validEmail(request.getEmail());
                if (!validEmail) {
                    throw new AuthenticationException(super.getMessage("message.account.validate.account.email.exists"));
                }

                // generate password + random salt value SHA-256
                try {
                    String saltValue = UUID.randomUUID().toString();
                    String hashedPassword = BusinessCommon.preparePassword(request.getPassword(), saltValue);

                    account.setPassword(hashedPassword);
                    account.setSaltValue(saltValue);
                    account.setProvider(AuthProvider.LOCAL.getValue());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                account.setRoles(roles);
                rs = accountManagementRepo.save(account);
            } else {
                Account oldAccount = accountManagementRepo.findById(request.getId()).orElse(null);
                if (oldAccount == null) {
                    throw new AuthenticationException(super.getMessage("message.account.validate.account.not.exists"));
                }

                if (oldAccount.getEmail() != null && !oldAccount.getEmail().equals(account.getEmail())) {
                    boolean validEmail = this.validEmail(account.getEmail());
                    if (!validEmail) {
                        throw new AuthenticationException(super.getMessage("message.account.validate.account.email.exists"));
                    }
                }

                // update password if change
                try {
                    String hashedUpdatePassword = BusinessCommon.preparePassword(request.getPassword(), oldAccount.getSaltValue());
                    if (!hashedUpdatePassword.equals(oldAccount.getPassword())) {
                        String saltValueNew = UUID.randomUUID().toString();
                        String hashedPassword = BusinessCommon.preparePassword(request.getPassword(), saltValueNew);

                        oldAccount.setPassword(hashedPassword);
                        oldAccount.setSaltValue(saltValueNew);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                oldAccount.setRoles(roles);
                oldAccount.setAddress(account.getAddress());
                oldAccount.setCitizenId(account.getCitizenId());
                oldAccount.setEmail(account.getEmail());
                oldAccount.setFullName(account.getFullName());
                oldAccount.setImageUrl(account.getImageUrl());
                oldAccount.setIssuedBy(account.getIssuedBy());
                oldAccount.setIssuedDate(account.getIssuedDate());
                oldAccount.setSalary(account.getSalary());
                oldAccount.setActive(account.getActive());
                oldAccount.setDeleted(account.getDeleted());

                rs = accountManagementRepo.save(oldAccount);
            }
        } catch (AuthenticationException aEx) {
            throw new AuthenticationException(aEx.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }

        return rs;
    }

    private boolean validUserName(String username) {
        Account account = accountManagementRepo.findByUsernameAndDeleted(username, 0);
        if (account != null) return false;
        return true;
    }

    private boolean validEmail(String email) {
        Account account = accountManagementRepo.findByEmailAndDeleted(email, 0);
        if (account != null) return false;
        return true;
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
