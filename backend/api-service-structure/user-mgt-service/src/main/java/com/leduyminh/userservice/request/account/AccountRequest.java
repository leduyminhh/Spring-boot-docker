package com.leduyminh.userservice.request.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class AccountRequest {

    private Long id;
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
