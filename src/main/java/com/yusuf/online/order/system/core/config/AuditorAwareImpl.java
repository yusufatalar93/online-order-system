package com.yusuf.online.order.system.core.config;

import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@EnableJpaAuditing(auditorAwareRef="auditorAwareImpl")
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!Objects.isNull(authentication) && authentication.getPrincipal() instanceof UserDetails) {
      return Optional.of(((UserDetails) authentication.getPrincipal()).getUsername());
    } else {
      return Optional.of("SYSTEM");
    }
  }
}