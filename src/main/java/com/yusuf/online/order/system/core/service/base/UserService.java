package com.yusuf.online.order.system.core.service.base;

import com.yusuf.online.order.system.core.model.dto.UserDTO;

public interface UserService {

  UserDTO findUserByUserName(String username);

  UserDTO getCurruntUser();
}
