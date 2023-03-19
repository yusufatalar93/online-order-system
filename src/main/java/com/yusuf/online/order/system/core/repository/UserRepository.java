package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


  Optional<User> findByEmail(String email);

}
