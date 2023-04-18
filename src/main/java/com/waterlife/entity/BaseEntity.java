package com.waterlife.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@MappedSuperclass
@Getter @Setter(PROTECTED)
public abstract class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdTime;

    @LastModifiedDate
    protected LocalDateTime lastEditedTime;
}