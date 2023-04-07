package br.com.agreedpurchase.adapter.controller.response;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.entity.ItemEntity;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
public class PurchaseDataResponse implements Serializable {

  private Date date;
  private BigDecimal discount;
  private BigDecimal rate;
  private List<ConsumerDataResponse> consumerDataResponses;

  public PurchaseDataResponse(Buy buy) {
    date = buy.getDate();
    discount = buy.getDiscount();
    consumerDataResponses = new ArrayList<>();
    if(buy.getItems() != null) {
      for(Item source : buy.getItems()) {
        ConsumerDataResponse consumerDataResponse =
            ConsumerDataResponse.builder()
                .name(source.getPerson())
                .amount(source.getAmount())
                .build();
        consumerDataResponses.add(consumerDataResponse);
      }
    }
  }

}
