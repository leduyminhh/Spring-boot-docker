package com.leduyminh.userservice.dtos.role;

import lombok.Data;

@Data
public class RoleDTO {

    private Long id;

    private String name;
    private String description;
    private Integer active;
    private Integer deleted;
}
