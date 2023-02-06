package com.leduyminh.userservice.entities;

import com.leduyminh.commons.entities.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Role extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Integer active;
    private Integer deleted;
}
