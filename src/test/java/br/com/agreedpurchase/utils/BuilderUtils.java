package br.com.agreedpurchase.utils;

import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BuilderUtils {

  public static Buy getBuyWhitItems(BigDecimal fee, BigDecimal discount, String discountType) {
    return Buy.builder()
        .date(new Date())
        .discount(discount)
        .discountType(discountType)
        .fee(fee)
        .items(getItems())
        .build();
  }

  public static Buy getBuy(BigDecimal fee, BigDecimal discount, String discountType) {
    return Buy.builder()
        .date(new Date())
        .discount(discount)
        .discountType(discountType)
        .fee(fee)
        .build();
  }

  public static Set<Item> getItems() {
    Set<Item> items = new HashSet<>();
    items.add(getItem(new BigDecimal(10), "Carla", "Refrigerante"));
    items.add(getItem(new BigDecimal(35), "Hugo", "Cheeseburguer"));
    items.add(getItem(new BigDecimal(30), "Carla", "Hamburguer"));
    return items;
  }

  public static Item getItem(BigDecimal amount, String person, String description) {
    return Item.builder()
        .descripton(description)
        .amount(amount)
        .person(person)
        .build();
  }
}
