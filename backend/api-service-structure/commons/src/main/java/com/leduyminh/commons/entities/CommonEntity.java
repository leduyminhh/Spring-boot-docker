package com.leduyminh.commons.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonEntity implements Serializable {

    protected Integer active = 1;
    protected Integer deleted = 0;

    @Column(updatable = false)
    @CreatedBy
    protected String createdBy;

    @Column(updatable = false)
    @CreatedDate
    protected Date createdDate;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    protected Date lastModifiedDate;
}
