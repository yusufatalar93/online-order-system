package com.yusuf.online.order.system.core.model.dto.base;

import java.util.Date;
import lombok.Data;

@Data
public class BaseDTO <U>{

  protected U createdBy;

  protected Date creationDate;

  protected U lastModifiedBy;

  protected Date lastModifiedDate;

  private Integer version;

}
