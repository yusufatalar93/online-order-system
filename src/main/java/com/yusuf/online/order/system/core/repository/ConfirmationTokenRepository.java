package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

  ConfirmationToken findByConfirmationToken(String confirmationToken);

}
