package com.yusuf.online.order.system.core.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

  E convertToEntity(D dto);

  D convertToDTO(E entity);

  List<E> convertAllToEntity(List<D> dto);

  List<D> convertAllToDTO(List<E> entity);
}
