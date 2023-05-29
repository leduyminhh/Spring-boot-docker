package com.leduyminh.userservice.repository.inf;

import com.leduyminh.userservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountManagementRepo extends JpaRepository<Account, Long> {
}
