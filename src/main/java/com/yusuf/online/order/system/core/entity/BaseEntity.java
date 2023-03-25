package com.yusuf.online.order.system.core.entity;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<U>{
  @Column(name = "CREATED_BY", updatable = false)
  @CreatedBy
  protected U createdBy;
  @Column(name = "CREATION_DATE", updatable = false)
  @CreatedDate
  @Temporal(TIMESTAMP)
  protected LocalDateTime creationDate;

  @LastModifiedBy
  protected U lastModifiedBy;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  protected LocalDateTime lastModifiedDate;

  @Version
  @Column(name = "VERSION")
  private Integer version;

}