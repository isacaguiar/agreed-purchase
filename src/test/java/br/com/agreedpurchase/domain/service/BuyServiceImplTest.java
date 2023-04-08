package br.com.agreedpurchase.domain.service;

import static br.com.agreedpurchase.utils.BuilderUtils.getBuy;
import static br.com.agreedpurchase.utils.BuilderUtils.getItem;
import static org.junit.jupiter.api.Assertions.*;

import br.com.agreedpurchase.domain.exception.InvalidDiscountTypeException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import br.com.agreedpurchase.domain.port.PersistencePort;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

  static Stream<Arguments> executeAddFeeAndDiscountTestSource() throws Exception {
    return Stream.of(
        Arguments.of("PERCENT", new BigDecimal(74.03).setScale(2, RoundingMode.HALF_UP)),
        Arguments.of("DELIVERY", new BigDecimal(77.95))
    );
  }

  @ParameterizedTest
  @MethodSource("executeAddFeeAndDiscountTestSource")
  void shouldSuccessWhenExecuteAddFeeAndDiscount(String discountType, BigDecimal expected) {
    BigDecimal actual = buyService.addFeeAndDiscount(loadBuy(discountType));

    assertEquals(actual.setScale(2, RoundingMode.HALF_UP), expected.setScale(2, RoundingMode.HALF_UP));//PERCENT - new BigDecimal(74.50)
  }

  @Test
  void shouldThrowsExceptionWhenExecuteAddFeeAndDiscount() {
    assertThrows(InvalidDiscountTypeException.class, () -> {
      buyService.addFeeAndDiscount(loadBuy(INVALID));
    });
  }

  @Test
  void shouldSuccessWhenExecuteGetAmountWithoutFees() {
    BigDecimal actual =
        buyService.getAmountWithoutFees(loadBuy(PERCENT));

    assertEquals(actual, new BigDecimal(75.95).setScale(2, RoundingMode.HALF_UP));
  }

  @Test
  void shouldSuccessWhenExecuteCalculatePercent() {
    BigDecimal actual = buyService.calculatePercent(new BigDecimal(10), new BigDecimal(5), new BigDecimal(2));

    BigDecimal expected = new BigDecimal(25);

    assertEquals(actual, expected.setScale(2, RoundingMode.HALF_UP));
  }

  @Test
  void shouldSuccessWhenGroupByPerson() {
    Map<String, BigDecimal> mapPerson =
        buyService.groupAmountByPerson(loadBuy(PERCENT));
    assertNotNull(mapPerson);
    assertEquals(2 , mapPerson.size());
  }

  private Buy loadBuy(String discountType) {
    Set<Item> items = new HashSet<>();
    items.add(getItem(new BigDecimal(9.80), "Carla", "Refrigerante"));
    items.add(getItem(new BigDecimal(35.90), "Hugo", "Cheeseburguer"));
    items.add(getItem(new BigDecimal(30.25), "Carla", "Hamburguer"));

    Buy buy = getBuy(new BigDecimal(10), new BigDecimal(8), discountType);
    buy.setItems(items);
    return buy;
  }

}