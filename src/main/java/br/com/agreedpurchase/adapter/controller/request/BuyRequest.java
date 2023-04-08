package br.com.agreedpurchase.adapter.controller.request;

import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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

    if(this.itemRequests != null) {
      for(ItemRequest source : itemRequests) {
        Item target = Item.builder().build();
        BeanUtils.copyProperties(source , target);
        if (buy.getItems() == null) {
          buy.setItems(new HashSet<>());
        }
        buy.getItems().add(target);
      }
    }

    return buy;
  }




}
