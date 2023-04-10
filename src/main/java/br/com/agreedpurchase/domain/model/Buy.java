package br.com.agreedpurchase.domain.model;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.entity.ItemEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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

  private Long id;
  private BigDecimal fee;
  private BigDecimal discount;

  private String discountType;

  private Date date;

  private Set<Item> items;

  Map<Long, BigDecimal> mapPerson;

  Map<Long, BigDecimal> mapPersonAddFee;

  public BuyEntity toEntity() {
    BuyEntity buyEntity = BuyEntity.builder()
        .discount(discount)
        .discountType(discountType)
        .fee(fee)
        .date(new Date())
        .build();

    if (items != null) {
      for (Item item : items) {
        ItemEntity itemEntity = ItemEntity.builder()
            .amount(item.getAmount())
            .descripton(item.getDescripton())
            .friend(item.getFriend() != null ? item.getFriend().toEntity() : null)
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
