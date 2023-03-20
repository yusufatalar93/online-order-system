package com.yusuf.online.order.system.core.model.dto;

import com.yusuf.online.order.system.core.enums.UserType;
import com.yusuf.online.order.system.core.model.dto.base.BaseDTO;
import lombok.Data;

@Data
public class UserDTO extends BaseDTO<String> {

  private Integer id;
  private String email;
  private String password;
  private String address;
  private String businessName;
  private UserType userType;

}
