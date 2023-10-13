package com.leduyminh.tracking.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public class Tracking {

    private String username;
    private String level;
    private String method;
    private String methodName;
    private String system;
    private String ipAddress;
    private String className;
    private String messageError;
    private Long timeRequest;
    private Date actionDate;
}
