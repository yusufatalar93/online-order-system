package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.entity.User;
import com.yusuf.online.order.system.core.mapper.UserMapper;
import com.yusuf.online.order.system.core.model.dto.UserDTO;
import com.yusuf.online.order.system.core.repository.UserRepository;
import com.yusuf.online.order.system.core.service.base.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SimpleUserService implements UserService {


  private final UserRepository userRepository;

  private final UserMapper mapper;


  @Override
  public UserDTO findUserByUserName(String username) {
    final User user = userRepository.findByEmail(username).orElseThrow(
        () -> new EntityNotFoundException(
            String.format("%s kullanıcı adına sahip bir kullanıcı bulunamadı.", username)));
    return mapper.convertToDTO(user);
  }

  @Override
  public UserDTO getCurruntUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    return findUserByUserName(currentPrincipalName);
  }


}
