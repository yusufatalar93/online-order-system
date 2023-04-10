package com.yusuf.online.order.system.auth.web.rest;

import com.yusuf.online.order.system.auth.model.AuthenticationRequest;
import com.yusuf.online.order.system.auth.model.AuthenticationResponse;
import com.yusuf.online.order.system.auth.model.VerificationUrlModel;
import com.yusuf.online.order.system.auth.service.AuthenticationService;
import com.yusuf.online.order.system.auth.model.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;


  // Burada url dönemeyecektim. Postmanden otomatik test etmek için ekledim.
  @PostMapping("/register")
  public ResponseEntity<VerificationUrlModel> register(
      @Valid @RequestBody UserRegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerUser(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
    @Valid  @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @GetMapping(value="/confirm-account/{value}")
  public ResponseEntity<?> confirmUserAccount(@PathVariable String value) {
    return service.confirmEmail(value);
  }

}
