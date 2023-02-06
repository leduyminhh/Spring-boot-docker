package com.leduyminh.userservice.controller;

import com.leduyminh.userservice.dtos.account.AccountDTO;
import com.leduyminh.userservice.entities.Account;
import com.leduyminh.userservice.repository.inf.AccountManagementRepo;
import com.leduyminh.userservice.request.account.AccountRequest;
import com.leduyminh.userservice.service.impl.AccountManagementServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AccountManagementController.class)
public class AccountManagementControllerTest {

    @LocalServerPort
    private int port;

    @Mock
    AccountManagementRepo accManagementRepo;

    @InjectMocks
    AccountManagementServiceImpl accManagementService;

    @Autowired
    private TestRestTemplate restTemplate;

    public AccountManagementControllerTest() {
    }

    @DisplayName("Create success account")
    @Test
    public void whenCreateAccount_thenStatus201() throws Exception {
//
//        AccountRequest request = this.givenAccountRequest();
//        ResponseEntity<Account> response = restTemplate.postForEntity(
//                new URL("http://localhost:" + port + "/accounts").toString(), request, Account.class);
//        assertEquals(null, response.getBody());
    }

//    private Account givenAccount() {
//        Account account = new Account();
//        account.setFullName("Lê Minh");
//        account.setUsername("minhld");
//        account.setPassword("123456");
//        account.setEmail("ldm.lmht@gmail.com");
//        account.setActive(1);
//        account.setDeleted(0);
//
//        return account;
//    }
//
//    private AccountRequest givenAccountRequest() {
//        AccountRequest account = new AccountRequest();
//        account.setFullName("Lê Minh");
//        account.setUsername("minhld");
//        account.setPassword("123456");
//        account.setEmail("ldm.lmht@gmail.com");
//        account.setActive(1);
//        account.setDeleted(0);
//
//        return account;
//    }
//
//    private AccountDTO givenAccountDTO() {
//        AccountDTO account = new AccountDTO();
//        account.setFullName("Lê Minh");
//        account.setUsername("minhld");
//        account.setPassword("123456");
//        account.setEmail("ldm.lmht@gmail.com");
//        account.setActive(1);
//        account.setDeleted(0);
//
//        return account;
//    }
}
