package br.com.agreedpurchase.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Buy {
  private BigDecimal fee;
  private BigDecimal discount;
  private String discountType;
  private Date date;
  private Set<Item> items;
  Map<String, BigDecimal> mapPerson;
  Map<String, BigDecimal> mapPersonAddFee;
}
