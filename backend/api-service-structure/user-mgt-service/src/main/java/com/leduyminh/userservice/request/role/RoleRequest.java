package com.leduyminh.userservice.request.role;

import lombok.Data;

@Data
public class RoleRequest {

    private Long id;

    private String name;
    private String description;
    private Integer active;
    private Integer deleted;
}
