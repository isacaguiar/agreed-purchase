package br.com.agreedpurchase.adapter.controller.request;

import br.com.agreedpurchase.domain.model.Item;
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

  public Item toModel() {
    return Item.builder()
        .descripton(descripton)
        .amount(amount)
        .person(person)
        .build();
  }
}
