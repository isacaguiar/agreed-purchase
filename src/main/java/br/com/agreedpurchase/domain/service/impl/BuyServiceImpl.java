package br.com.agreedpurchase.domain.service.impl;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.DELIVERY;
import static br.com.agreedpurchase.domain.utils.ConstantsUtils.PERCENT;

import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import br.com.agreedpurchase.domain.service.BuyService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuyServiceImpl implements BuyService {

  public Buy buy(Buy buy) throws BusinessException {
    process(buy);
    return buy;
  }

  private void process(Buy buy) {
    //Agroup amount by person
    buy.setMapPerson(groupAmountByPerson(buy));

    //Add Fee and Discount
    BigDecimal amount = addFeeAndDiscount(buy);

    //Add ammount after fee and discount
    buy.setMapPersonAddFee(addFeeAndDiscountForPerson(buy, amount));
  }

  private Map<String, BigDecimal> addFeeAndDiscountForPerson(Buy buy, BigDecimal amount) {
    Map<String, BigDecimal> mapPersonAddFee = new HashMap<>();
    for (Map.Entry<String, BigDecimal> entry : buy.getMapPerson().entrySet()) {
      BigDecimal amountAdjustment = calculatePercent(entry.getValue(), amount, getAmountWithoutFees(buy));
      log.info("Adjustment: ".concat(amountAdjustment.toString()));
      mapPersonAddFee.put(entry.getKey(), amountAdjustment);
      log.info("Amount adjustment: ".concat(amountAdjustment.toString()));
    }
    return mapPersonAddFee;
  }

  private BigDecimal addFeeAndDiscount(Buy buy) throws BusinessException {
    BigDecimal amount = getAmountWithoutFees(buy);
    log.info("Amount: ".concat(amount.toString()));

    switch (buy.getDiscountType().toUpperCase()) {
      case DELIVERY:
        amount = amount.add(buy.getFee());
        log.info("Applied delivery: ".concat(amount.toString()));
        break;
      case PERCENT:
        amount = amount.add(calculatePercent(amount, buy.getDiscount(), new BigDecimal(100)));
        log.info("Applied percent: ".concat(amount.toString()));
        break;
      default:
        throw new BusinessException("Invalid discount type");
    }
    return amount.subtract(buy.getDiscount());
  }

  private BigDecimal getAmountWithoutFees(Buy buy) {
    BigDecimal amount = new BigDecimal(0);
    if (buy.getItems() != null) {
      for (Item item : buy.getItems()) {
        amount = amount.add(item.getAmount());
      }
    }
    return amount.setScale(2, RoundingMode.HALF_UP);
  }

  private Map<String, BigDecimal> groupAmountByPerson(Buy buy) {
    Map<String, BigDecimal> mapPerson = new HashMap<>();
    for (Item i : buy.getItems()) {
      BigDecimal amount = mapPerson.get(i.getPerson()) == null
          ? i.getAmount() : mapPerson.get(i.getPerson()).add(i.getAmount());
      mapPerson.put(i.getPerson(), amount);

    }
    return mapPerson;
  }

  public BigDecimal calculatePercent(BigDecimal amount, BigDecimal multiply, BigDecimal divide) {
    BigDecimal amountCalculate =
        amount.multiply(multiply).divide(divide, 2, RoundingMode.HALF_UP);
    log.info("Percent: ".concat(multiply.toString()));
    return amountCalculate;
  }

}
