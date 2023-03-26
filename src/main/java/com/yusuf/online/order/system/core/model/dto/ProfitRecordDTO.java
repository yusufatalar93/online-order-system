package com.yusuf.online.order.system.core.model.dto;

import com.yusuf.online.order.system.core.model.dto.base.BaseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProfitRecordDTO extends BaseDTO<String> {



  private Integer id;
  private Integer seller;
  private LocalDateTime date;
  private Long total;


}
