package com.leduyminh.userservice.dtos.account;

import com.leduyminh.userservice.entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class AccountLoginDTO {
    private String fullName;
    private String username;
    private String provider;
    private String avatar;
    private String email;
    private List<Role> roles;
    private String token;
}
