package com.leduyminh.userservice.entities;

import com.leduyminh.commons.entities.CommonEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role extends CommonEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
}
