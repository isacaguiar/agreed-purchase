package br.com.agreedpurchase.domain.service;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import br.com.agreedpurchase.domain.port.PersistencePort;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyService {

  @Autowired
  PersistencePort persistencePort;

  public Buy buy(Buy buy) {
    BuyEntity buyEntity = persistencePort.buy(buy.toEntity());

    Map<String, BigDecimal> mapPerson = groupAmountByPerson(buyEntity.toModel());

    buy = getBuyTotaled(buy, mapPerson);

    //addFee

    return buy;
  }

  private void totalToBePaid() {

  }

  private Map<String, BigDecimal> groupAmountByPerson(Buy buy) {
    Map<String, BigDecimal> mapPerson = new HashMap<>();
    for ( Item i : buy.getItems() ) {
      BigDecimal amount = mapPerson.get(i.getPerson()) == null
          ? i.getAmount() : mapPerson.get(i.getPerson()).add(i.getAmount());
      mapPerson.put(i.getPerson(), amount);
    }
    return mapPerson;
  }

  private Buy getBuyTotaled(Buy buy, Map<String, BigDecimal> mapTotaled) {
    Buy buyTotaled = Buy.builder()
        .discount(buy.getDiscount())
        .rate(buy.getRate())
        .date(buy.getDate()).build();
    if (buyTotaled.getItems() == null) {
      buyTotaled.setItems(new HashSet<>());
    }
    for (Map.Entry<String, BigDecimal> entry : mapTotaled.entrySet()) {
      Item item = Item.builder()
          .descripton("Total")
          .amount(entry.getValue())
          .person(entry.getKey()).build();
      buyTotaled.getItems().add(item);
    }
    return buyTotaled;
  }


}
