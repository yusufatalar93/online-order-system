package com.yusuf.online.order.system.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


  Optional<User> findByEmail(String email);

}
