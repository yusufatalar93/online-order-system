package com.yusuf.online.order.system.core.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@EnableJpaAuditing(auditorAwareRef="auditorAwareImpl")
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (currentUser instanceof UserDetails) {
      return Optional.of(((UserDetails) currentUser).getUsername());
    } else {
      return Optional.of("SYSTEM");
    }
  }
}