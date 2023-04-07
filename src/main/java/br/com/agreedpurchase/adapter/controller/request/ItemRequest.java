package br.com.agreedpurchase.adapter.controller.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
  private String descripton;
  private BigDecimal amount;
  private String person;
}
