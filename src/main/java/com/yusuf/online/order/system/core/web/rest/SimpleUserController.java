package com.yusuf.online.order.system.core.web.rest;

import com.yusuf.online.order.system.core.model.dto.UserDTO;
import com.yusuf.online.order.system.core.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class SimpleUserController {

  private final UserService userService;


  @GetMapping("find-user-by-username/{username}")
  public ResponseEntity<UserDTO> findUserByUserName(@PathVariable String username) {
    return ResponseEntity.ok(userService.findUserByUserName(username));
  }


}
