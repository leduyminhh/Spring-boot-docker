package com.leduyminh.userservice.dtos.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leduyminh.commons.validator.annotation.FieldNotNullAndBlank;
import lombok.Data;

import java.util.Date;

@Data
public class AccountDTO {

    private Long id;

    @FieldNotNullAndBlank(message = "{message.validator.fullName.notNullAndBlank}")
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

    @JsonIgnore
    private String password;
}
