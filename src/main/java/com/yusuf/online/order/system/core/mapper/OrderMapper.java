package com.yusuf.online.order.system.core.mapper;

import com.yusuf.online.order.system.core.entity.Order;
import com.yusuf.online.order.system.core.model.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order, OrderDTO> {

}


