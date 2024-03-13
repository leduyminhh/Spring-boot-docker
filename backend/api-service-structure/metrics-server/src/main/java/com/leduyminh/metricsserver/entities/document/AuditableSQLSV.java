package com.leduyminh.metricsserver.entities.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableSQLSV {

//    @Column(name = "CreatedBy", updatable = false)
//    @CreatedBy
//    protected String createdBy;

//    @LastModifiedBy
//    @Column(name = "LastModifiedBy")
//    protected String lastModifiedBy;

    @Column(name = "CreatedDate", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @LastModifiedDate
    @Column(name = "LastModifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;
}
