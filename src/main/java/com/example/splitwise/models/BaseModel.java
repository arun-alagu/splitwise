package com.example.splitwise.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
    @Id
    @GeneratedValue
    private UUID id;
    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdOn;
    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastModified;

}
