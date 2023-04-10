package com.yusuf.online.order.system.auth.model;

import com.yusuf.online.order.system.core.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class VerificationUrlModel {

  private String url;


}
