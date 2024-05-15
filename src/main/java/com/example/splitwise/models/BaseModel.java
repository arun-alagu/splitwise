package com.example.splitwise.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
public class BaseModel {
    @Id
    private String id;
    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdOn;
    @LastModifiedDate
    private Date lastModified;

}
