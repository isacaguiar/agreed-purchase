package br.com.agreedpurchase.domain.model;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.entity.ItemEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
  private BigDecimal rate;
  private BigDecimal discount;

  private String discountType;

  private Date date;

  private Set<Item> items;

  public BuyEntity toEntity() {
    BuyEntity buyEntity = BuyEntity.builder()
        .discount(discount)
        .rate(rate)
        .date(new Date())
        .build();

    if (items != null) {
      for (Item item : items) {
        ItemEntity itemEntity = ItemEntity.builder()
            .amount(item.getAmount())
            .descripton(item.getDescripton())
            .person(item.getPerson())
            .buy(buyEntity)
            .build();
        if (buyEntity.getItemEntities() == null) {
          buyEntity.setItemEntities(new HashSet<>());
        }
        buyEntity.getItemEntities().add(itemEntity);
      }
    }

    return buyEntity;
  }
}
