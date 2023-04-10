package com.yusuf.online.order.system.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringSwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";
    io.swagger.v3.oas.models.security.SecurityScheme securityScheme = new io.swagger.v3.oas.models.security.SecurityScheme();
    securityScheme.setName(securitySchemeName);
    securityScheme.setType(Type.HTTP);
    securityScheme.setScheme("bearer");
    securityScheme.bearerFormat("JWT");

    return new OpenAPI()
        .info(new Info().title("OOP")
            .description("Online Order System")
            .version("1.0"))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(securitySchemeName, securityScheme)
        );
  }


}


