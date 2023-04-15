package br.com.agreedpurchase.adapter.controller.request;

import br.com.agreedpurchase.domain.model.Buy;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyRequest {

  private BigDecimal fee;
  private BigDecimal discount;

  private String discountType;
  private Set<ItemRequest> itemRequests;

  public Buy toModel() {
    Buy buy = Buy.builder()
        .discount(discount)
        .discountType(discountType)
        .fee(fee)
        .date(new Date())
        .build();
    loadItems(buy);
    return buy;
  }

  private void loadItems(Buy buy) {
    if(this.itemRequests != null) {
      for(ItemRequest source : itemRequests) {
        if (buy.getItems() == null) {
          buy.setItems(new HashSet<>());
        }
        buy.getItems().add(source.toModel());
      }
    }
  }

}
