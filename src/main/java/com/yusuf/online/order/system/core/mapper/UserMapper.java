package com.yusuf.online.order.system.core.mapper;

import com.yusuf.online.order.system.core.entity.User;
import com.yusuf.online.order.system.core.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User, UserDTO> {

}


