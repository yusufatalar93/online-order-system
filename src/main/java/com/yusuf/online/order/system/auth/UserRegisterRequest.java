package com.yusuf.online.order.system.auth;

import com.yusuf.online.order.system.core.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {


  @Email
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  private String address;
  private String businessName;
  @NotNull
  private UserType userType;
}
