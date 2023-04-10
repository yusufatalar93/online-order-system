package com.yusuf.online.order.system.auth.service;


import com.yusuf.online.order.system.auth.model.AuthenticationRequest;
import com.yusuf.online.order.system.auth.model.AuthenticationResponse;
import com.yusuf.online.order.system.auth.model.UserRegisterRequest;
import com.yusuf.online.order.system.auth.model.VerificationUrlModel;
import com.yusuf.online.order.system.core.entity.ConfirmationToken;
import com.yusuf.online.order.system.core.entity.User;
import com.yusuf.online.order.system.core.enums.UserType;
import com.yusuf.online.order.system.core.repository.ConfirmationTokenRepository;
import com.yusuf.online.order.system.core.repository.UserRepository;
import com.yusuf.online.order.system.core.service.EmailService;
import com.yusuf.online.order.system.core.service.util.Messages;
import jakarta.persistence.EntityExistsException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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

  private final ConfirmationTokenRepository confirmationTokenRepository;

  private final EmailService emailService;

  public VerificationUrlModel registerUser(UserRegisterRequest request) {
    final Optional<User> userByMailAddress = repository.findByEmail(request.getEmail());
    if (userByMailAddress.isPresent()) {
      final String errorMessage;
      if (userByMailAddress.get().isEnabled()) {
        errorMessage = String.format(Messages.getMessageForLocale("user.exist.exception"),
            request.getEmail());
      } else {
        errorMessage = String.format(Messages.getMessageForLocale("user.not.verified.exception"),
            request.getEmail());
      }

      log.error("An error occurred while creating the user. Error Message : {}", errorMessage);
      throw new EntityExistsException(errorMessage);
    }
    if (UserType.SELLER.equals(request.getUserType()) && Objects.isNull(
        request.getBusinessName())) {
      final String errorMessage = Messages.getMessageForLocale(
          " mandatory.business.name.field.exception");
      log.error("An error occurred while creating the user. Error Message : {}", errorMessage);
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
    log.info("{} user created.", request.getEmail());
    ConfirmationToken confirmationToken = new ConfirmationToken(user);

    confirmationTokenRepository.save(confirmationToken);

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(user.getEmail());
    mailMessage.setSubject("Complete Registration!");
    final String link = "http://localhost:8080/api/auth/confirm-account/"
        + confirmationToken.getConfirmationToken();
    mailMessage.setText("To confirm your account, please click here : "
        + link);
    emailService.sendEmail(mailMessage);

    System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
    return new VerificationUrlModel(link);

  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    User user = repository.findByEmailAndEnabled(request.getEmail(), true)
        .orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    log.info("{} is authenticated.", user.getUsername());
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }


  public ResponseEntity<?> confirmEmail(String confirmationToken) {
    ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(
        confirmationToken);
    String email = "";
    if (token != null) {
      email = token.getUser().getEmail();
      User user = repository.findByEmailIgnoreCase(email);
      user.setEnabled(true);
      repository.save(user);
      final String successMessage = Messages.getMessageForLocale("successful.email.verification");
      log.info(successMessage);
      return ResponseEntity.ok(successMessage);
    }
    final String failMessage =
        Messages.getMessageForLocale("fail.email.verification");
    log.error(failMessage);
    return ResponseEntity.badRequest().body(failMessage);
  }


}
