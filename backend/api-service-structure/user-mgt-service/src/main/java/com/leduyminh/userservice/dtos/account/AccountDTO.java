package com.leduyminh.userservice.dtos.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leduyminh.commons.validator.annotation.FieldNotNullAndBlank;
import com.leduyminh.userservice.entities.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AccountDTO {

    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String address;
    private String citizenId;
    private String issuedBy;
    private Date issuedDate;
    private Date dob;
    private Long salary;
    private Integer active;
    private Integer deleted;
    private List<Role> roles;
}
