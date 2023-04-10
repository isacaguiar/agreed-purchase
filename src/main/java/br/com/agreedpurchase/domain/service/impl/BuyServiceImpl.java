package br.com.agreedpurchase.domain.service.impl;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.DELIVERY;
import static br.com.agreedpurchase.domain.utils.ConstantsUtils.PERCENT;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import br.com.agreedpurchase.domain.port.PersistencePort;
import br.com.agreedpurchase.domain.service.BuyService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuyServiceImpl implements BuyService {

  @Autowired
  PersistencePort persistencePort;

  public Buy buy(Buy buy) throws BusinessException {
    BuyEntity buyEntity = persistencePort.buy(buy.toEntity());

    process(buy, buyEntity);

    return buy;
  }

  private void process(Buy buy, BuyEntity buyEntity) {
    //Agroup amount by person
    buy.setMapPerson(groupAmountByPerson(buyEntity.toModel()));

    //Add Fee and Discount
    BigDecimal amount = addFeeAndDiscount(buy);

    //Add ammount after fee and discount
    buy.setMapPersonAddFee(addFeeAndDiscountForPerson(buy, amount));
  }

  public Buy loadById(Long id) throws BusinessException {
    Buy buy;
    Optional<BuyEntity> optionalBuyEntity = persistencePort.getBuyEntityById(id);
    if (optionalBuyEntity.isPresent()) {
      BuyEntity buyEntity = optionalBuyEntity.get();
      buy = buyEntity.toModel();
      process(buy, buyEntity);
    } else {
      throw new BusinessException("Purchase Not Found");
    }
    return buy;
  }

  public List<Buy> loadAll() throws BusinessException {
    Buy buy;
    List<Buy> list = new ArrayList<>();
    List<BuyEntity> buyEntities = persistencePort.getBuyEntities();
    for (BuyEntity buyEntity : buyEntities) {
      buy = buyEntity.toModel();
      process(buy, buyEntity);
      list.add(buy);
    }
    return list;
  }

  public void delete(Long id) throws BusinessException {
    persistencePort.deleteBuyEntityById(id);
  }

  protected Map<Long, BigDecimal> addFeeAndDiscountForPerson(Buy buy, BigDecimal amount) {
    Map<Long, BigDecimal> mapPersonAddFee = new HashMap<>();
    for (Map.Entry<Long, BigDecimal> entry : buy.getMapPerson().entrySet()) {
      BigDecimal amountAdjustment = calculatePercent(entry.getValue(), amount, getAmountWithoutFees(buy));
      log.info("Adjustment: ".concat(amountAdjustment.toString()));
      mapPersonAddFee.put(entry.getKey(), amountAdjustment);
      log.info("Amount adjustment: ".concat(amountAdjustment.toString()));
    }
    return mapPersonAddFee;
  }

  protected BigDecimal addFeeAndDiscount(Buy buy) throws BusinessException {
    BigDecimal amount = getAmountWithoutFees(buy);
    log.info("Amount: ".concat(amount.toString()));

    switch (buy.getDiscountType().toUpperCase()) {
      case DELIVERY:
        amount = amount.add(buy.getFee());
        log.info("Applied delivery: ".concat(amount.toString()));
        break;
      case PERCENT:
        amount = amount.add(calculatePercent(amount , buy.getDiscount(), new BigDecimal(100)));
        log.info("Applied percent: ".concat(amount.toString()));
        break;
      default:
        throw new BusinessException("Invalid discount type");
    }
    return amount.subtract(buy.getDiscount());
  }

  protected BigDecimal getAmountWithoutFees(Buy buy) {
    BigDecimal amount = new BigDecimal(0);
    if(buy.getItems() != null) {
      for (Item item : buy.getItems()) {
        amount = amount.add(item.getAmount());
      }
    }
    return amount.setScale(2, RoundingMode.HALF_UP);
  }

  protected Map<Long, BigDecimal> groupAmountByPerson(Buy buy) {
    Map<Long, BigDecimal> mapPerson = new HashMap<>();
    for ( Item i : buy.getItems() ) {
      if(i.getFriend() != null) {
        BigDecimal amount = mapPerson.get(i.getFriend().getId()) == null
            ? i.getAmount() : mapPerson.get(i.getFriend().getId()).add(i.getAmount());
        mapPerson.put(i.getFriend().getId(), amount);
      } else {
        BigDecimal amount = mapPerson.get(0L) == null
            ? i.getAmount() : mapPerson.get(0L).add(i.getAmount());
        mapPerson.put(0L, amount);
      }
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
