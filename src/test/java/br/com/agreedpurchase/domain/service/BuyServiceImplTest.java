package br.com.agreedpurchase.domain.service;

import static br.com.agreedpurchase.utils.BuilderUtils.getBuyEntityWithItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.port.PersistencePort;
import br.com.agreedpurchase.utils.BuilderUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BuyServiceImpl.class})
class BuyServiceImplTest {

  private String PERCENT = "PERCENT";
  private String DELIVERY = "DELIVERY";
  private String INVALID = "INVALID";

  @Autowired
  BuyServiceImpl buyService;

  @MockBean
  PersistencePort persistencePort;

  @Test
  void shouldSuccessWhenExecuteBuy() {
    BigDecimal amount = new BigDecimal(100);
    BigDecimal discount = new BigDecimal(20);

    when(persistencePort.buy(any())).thenReturn(getBuyEntityWithItems(amount, discount,PERCENT));
    Buy buy = buyService.buy(BuilderUtils.loadBuyWithMapPersonAndFee(PERCENT));

    assertNotNull(buy);
    assertEquals(3, buy.getItems().size());
  }

  @Test
  void shouldSuccessWhenExecuteAddFeeAndDiscountForPerson() {
    BigDecimal amount = new BigDecimal(100);
    Map<String, BigDecimal> mapPersonAddFee =
        buyService.addFeeAndDiscountForPerson(BuilderUtils.loadBuyWithMapPerson(PERCENT), amount);

    assertNotNull(mapPersonAddFee);
    assertEquals(2, mapPersonAddFee.size());
  }

  static Stream<Arguments> executeAddFeeAndDiscountTestSource() throws Exception {
    return Stream.of(
        Arguments.of("PERCENT", new BigDecimal(74.03).setScale(2, RoundingMode.HALF_UP)),
        Arguments.of("DELIVERY", new BigDecimal(77.95))
    );
  }

  @ParameterizedTest
  @MethodSource("executeAddFeeAndDiscountTestSource")
  void shouldSuccessWhenExecuteAddFeeAndDiscount(String discountType, BigDecimal expected) {
    BigDecimal actual = buyService.addFeeAndDiscount(BuilderUtils.loadBuy(discountType));

    assertNotNull(actual);
    assertEquals(actual.setScale(2, RoundingMode.HALF_UP), expected.setScale(2, RoundingMode.HALF_UP));//PERCENT - new BigDecimal(74.50)
  }

  @Test
  void shouldThrowsExceptionWhenExecuteAddFeeAndDiscount() {
    assertThrows(BusinessException.class, () -> {
      buyService.addFeeAndDiscount(BuilderUtils.loadBuy(INVALID));
    });
  }

  @Test
  void shouldSuccessWhenExecuteGetAmountWithoutFees() {
    BigDecimal actual =
        buyService.getAmountWithoutFees(BuilderUtils.loadBuy(PERCENT));
    assertNotNull(actual);
    assertEquals(actual, new BigDecimal(75.95).setScale(2, RoundingMode.HALF_UP));
  }

  @Test
  void shouldSuccessWhenExecuteCalculatePercent() {
    BigDecimal actual = buyService.calculatePercent(new BigDecimal(10), new BigDecimal(5), new BigDecimal(2));
    BigDecimal expected = new BigDecimal(25);
    assertNotNull(expected);
    assertEquals(actual, expected.setScale(2, RoundingMode.HALF_UP));
  }

  @Test
  void shouldSuccessWhenGroupByPerson() {
    Map<String, BigDecimal> mapPerson =
        buyService.groupAmountByPerson(BuilderUtils.loadBuy(PERCENT));
    assertNotNull(mapPerson);
    assertEquals(2 , mapPerson.size());
  }

}