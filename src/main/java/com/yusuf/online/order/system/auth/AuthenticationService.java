package com.yusuf.online.order.system.auth;


import com.yusuf.online.order.system.config.JwtService;
import com.yusuf.online.order.system.core.config.Messages;
import com.yusuf.online.order.system.core.entity.User;
import com.yusuf.online.order.system.core.enums.UserType;
import com.yusuf.online.order.system.core.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse registerUser(UserRegisterRequest request) {
    if (repository.findByEmail(request.getEmail()).isPresent()) {

      final String errorMessage = String.format(Messages.getMessageForLocale("user.exist.exception"),
          request.getEmail());
      log.error("An error occurred while creating the user. Error Message : {}",errorMessage);
      throw new EntityExistsException(errorMessage);
    }
    if (UserType.SELLER.equals(request.getUserType()) && Objects.isNull(request.getBusinessName())){
      final String errorMessage = Messages.getMessageForLocale(" mandatory.business.name.field.exception");
      log.error("An error occurred while creating the user. Error Message : {}",errorMessage);
      throw new IllegalArgumentException(errorMessage);
    }
    User user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .address(request.getAddress())
        .userType(request.getUserType())
        .businessName(request.getBusinessName())
        .build();
    repository.save(user);
    log.info("{} user created.",request.getEmail());
    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    User user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    log.info("{} is authenticated.",user.getUsername());
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }


}
