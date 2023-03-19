package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.entity.User;
import com.yusuf.online.order.system.core.model.dto.UserDTO;
import com.yusuf.online.order.system.core.repository.UserRepository;
import com.yusuf.online.order.system.core.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {


  private final UserRepository userRepository;

  private final ModelMapper modelMapper;


  @Override
  public UserDTO findUserByUserName(String username) {
    final User user = userRepository.findByEmail(username).orElseThrow();
    return modelMapper.map(user, UserDTO.class);
  }


}
