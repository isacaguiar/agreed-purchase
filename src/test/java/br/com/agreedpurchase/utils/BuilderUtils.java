package br.com.agreedpurchase.utils;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.entity.ItemEntity;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

  public static BuyEntity getBuyEntity(BigDecimal fee, BigDecimal discount, String discountType) {
    return BuyEntity.builder()
        .date(new Date())
        .discount(discount)
        .discountType(discountType)
        .fee(fee)
        .build();
  }

  public static BuyEntity getBuyEntityWithItems(BigDecimal fee, BigDecimal discount, String discountType) {
    return BuyEntity.builder()
        .date(new Date())
        .discount(discount)
        .discountType(discountType)
        .fee(fee)
        .itemEntities(getItemEntities())
        .build();
  }

  public static Buy loadBuy(String discountType) {
    Set<Item> items = new HashSet<>();
    items.add(getItem(new BigDecimal(9.80), "Carla", "Refrigerante"));
    items.add(getItem(new BigDecimal(35.90), "Hugo", "Cheeseburguer"));
    items.add(getItem(new BigDecimal(30.25), "Carla", "Hamburguer"));

    Buy buy = getBuy(new BigDecimal(10), new BigDecimal(8), discountType);
    buy.setItems(items);
    return buy;
  }

  public static Buy loadBuyWithMapPerson(String discountType) {
    Map<String, BigDecimal> map = new HashMap<>();
    map.put("Carla", new BigDecimal(40));
    map.put("Hugo", new BigDecimal(12));

    Set<Item> items = new HashSet<>();
    items.add(getItem(new BigDecimal(15), "Carla", "Refrigerante"));
    items.add(getItem(new BigDecimal(12), "Hugo", "Cheeseburguer"));
    items.add(getItem(new BigDecimal(25), "Carla", "Hamburguer"));

    Buy buy = getBuy(new BigDecimal(10), new BigDecimal(8), discountType);
    buy.setMapPerson(map);
    buy.setItems(items);
    return buy;
  }

  public static Buy loadBuyWithMapPersonAndFee(String discountType) {
    Map<String, BigDecimal> mapFee = new HashMap<>();
    mapFee.put("Carla", new BigDecimal(45));
    mapFee.put("Hugo", new BigDecimal(14));

    Map<String, BigDecimal> map = new HashMap<>();
    map.put("Carla", new BigDecimal(40));
    map.put("Hugo", new BigDecimal(12));

    Set<Item> items = new HashSet<>();
    items.add(getItem(new BigDecimal(15), "Carla", "Refrigerante"));
    items.add(getItem(new BigDecimal(12), "Hugo", "Cheeseburguer"));
    items.add(getItem(new BigDecimal(25), "Carla", "Hamburguer"));

    Buy buy = getBuy(new BigDecimal(10), new BigDecimal(8), discountType);
    buy.setMapPerson(map);
    buy.setItems(items);
    buy.setMapPersonAddFee(mapFee);
    return buy;
  }

  public static Set<ItemEntity> getItemEntities() {
    Set<ItemEntity> items = new HashSet<>();
    items.add(getItemEntity(new BigDecimal(10), "Carla", "Refrigerante"));
    items.add(getItemEntity(new BigDecimal(35), "Hugo", "Cheeseburguer"));
    items.add(getItemEntity(new BigDecimal(30), "Carla", "Hamburguer"));
    return items;
  }

  public static ItemEntity getItemEntity(BigDecimal amount, String person, String description) {
    return ItemEntity.builder()
        .descripton(description)
        .amount(amount)
        .person(person)
        .build();
  }
}
