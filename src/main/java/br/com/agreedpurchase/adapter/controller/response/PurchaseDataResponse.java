package br.com.agreedpurchase.adapter.controller.response;

import br.com.agreedpurchase.domain.model.Buy;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDataResponse implements Serializable {

  private Date date;
  private BigDecimal discount;
  private String discountType;
  private BigDecimal fee;
  private List<ConsumerDataResponse> consumerDataResponses;

  private List<AmountToPayResponse> amountToPayResponses;

  public PurchaseDataResponse(Buy buy) {
    fee = buy.getFee();
    date = buy.getDate();
    discount = buy.getDiscount();
    discountType = buy.getDiscountType();
    consumerDataResponses = new ArrayList<>();
    if(buy.getMapPerson() != null) {
      for(Map.Entry<String, BigDecimal> entry : buy.getMapPerson().entrySet()) {
        ConsumerDataResponse consumerDataResponse =
            ConsumerDataResponse.builder()
                .name(entry.getKey())
                .amount(entry.getValue())
                .build();
        consumerDataResponses.add(consumerDataResponse);
      }
    }
    amountToPayResponses = new ArrayList<>();
    if(buy.getMapPersonAddFee() != null) {
      for(Map.Entry<String, BigDecimal> entry : buy.getMapPersonAddFee().entrySet()) {
        AmountToPayResponse amountToPayResponse =
            AmountToPayResponse.builder()
                .name(entry.getKey())
                .amount(entry.getValue())
                .build();
        amountToPayResponses.add(amountToPayResponse);
      }
    }
  }

}
