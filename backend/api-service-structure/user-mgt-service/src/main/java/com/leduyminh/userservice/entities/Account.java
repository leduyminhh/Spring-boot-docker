package com.leduyminh.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leduyminh.commons.entities.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Account extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String username;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    private String address;
    private String citizenId;
    private String issuedBy;
    private Date issuedDate;
    private Date dob;
    private Long salary;
    private Integer active;
    private Integer deleted;
    private String saltValue;

    @ManyToMany
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(
                    name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
}
