package com.waterlife.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime createdTime;
    private LocalDateTime lastEditedTime;
}