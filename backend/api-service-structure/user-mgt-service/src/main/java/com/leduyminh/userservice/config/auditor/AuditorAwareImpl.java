package com.leduyminh.userservice.config.auditor;

import com.leduyminh.userservice.entities.Account;
import com.leduyminh.userservice.repository.inf.AccountManagementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Account> {

    @Autowired
    private AccountManagementRepo accountManagementRepo;

    @Override
    public Optional<Account> getCurrentAuditor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = new Account();
        account.setUsername("minh");
        return Optional.of(account);
    }
}
