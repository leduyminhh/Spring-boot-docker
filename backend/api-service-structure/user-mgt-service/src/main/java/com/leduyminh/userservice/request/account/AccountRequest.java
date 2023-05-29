package com.leduyminh.userservice.request.account;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class AccountRequest {

    private Long id;
    @NotNull(message = "Họ và tên không được để trống")
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String address;
    private String citizenId;
    private String issuedBy;
    private Date issuedDate;
    private Date dob;
    private Long salary;
    private Integer active;
    private Integer deleted;
    private List<Long> roleIds;
}
