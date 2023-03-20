package com.yusuf.online.order.system.auth;

import com.yusuf.online.order.system.core.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {


  private String email;
  private String password;
  private String address;
  private String businessName;
  private UserType userType;
}
