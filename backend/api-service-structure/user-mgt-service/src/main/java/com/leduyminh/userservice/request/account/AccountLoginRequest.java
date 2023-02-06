package com.leduyminh.userservice.request.account;

import lombok.Data;

@Data
public class AccountLoginRequest {

    private String username;
    private String password;
}
