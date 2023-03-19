package com.yusuf.online.order.system.core.model.dto.base;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
public class BaseDTO <U>{

  protected U createdBy;

  protected Date creationDate;

  protected U lastModifiedBy;

  protected Date lastModifiedDate;

  private int version;

}
