package br.com.agreedpurchase.utils;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.DELIVERY;
import static br.com.agreedpurchase.domain.utils.ConstantsUtils.PERCENT;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BuilderUtils {

  public static List<Buy> loadPurchases() {
    List<Buy> list = new ArrayList<>();
    list.add(loadBuyWithMapPersonAndFee(PERCENT));
    list.add(loadBuyWithMapPersonAndFee(DELIVERY));
    return list;
  }

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

  public static PixResponse getPixResponse(String copyPaste) {
    return PixResponse.builder()
        .copyPaste(copyPaste)
        .build();
  }

  public static PixRequest getPixRequest(String pixKey,
                                          String description,
                                          String merchantName,
                                          String merchantCity,
                                          String txid,
                                          BigDecimal amount) {
    return PixRequest.builder()
        .pixKey(pixKey)
        .merchantName(merchantName)
        .merchantCity(merchantCity)
        .description(description)
        .txid(txid)
        .amount(amount)
        .build();
  }

  public static PixRequest loadPixRequest() {
    String pixKey = "isacaguiar@gmail.com";
    String description = "Test Agreed Purcharse";
    String merchantName = "Isac Aguiar";
    String merchantCity = "Salvador";
    String txid = "IVCA-23456987";
    BigDecimal amount = new BigDecimal(10);
    return getPixRequest(pixKey,description,merchantName,merchantCity,txid,amount);
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
}
