package br.com.agreedpurchase.domain.model;

import java.math.BigDecimal;
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
public class Charge {

  private Long id;
  private Item item;
  private Friend friend;
  private BigDecimal amount;

  //private ChargeE
}
