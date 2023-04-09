package com.yusuf.online.order.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringSwaggerConfig {

  @Bean
  public OpenAPI usersMicroserviceOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("OOP")
            .description("Online Order System")
            .version("1.0"));
  }


}


