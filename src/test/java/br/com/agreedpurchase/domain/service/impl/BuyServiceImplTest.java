package br.com.agreedpurchase.domain.service.impl;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.DELIVERY;
import static br.com.agreedpurchase.domain.utils.ConstantsUtils.INVALID;
import static br.com.agreedpurchase.domain.utils.ConstantsUtils.PERCENT;
import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.utils.BuilderUtils;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BuyServiceImpl.class})
class BuyServiceImplTest {

  @Autowired
  BuyServiceImpl buyService;

  @Test
  void shouldSuccessWhenExecuteBuy() {
    BigDecimal amount = new BigDecimal(100);
    BigDecimal discount = new BigDecimal(20);

    //when(persistencePort.buy(any())).thenReturn(getBuyEntityWithItems(amount, discount,PERCENT));
    Buy buy = buyService.buy(BuilderUtils.loadBuyWithMapPersonAndFee(PERCENT));

    assertNotNull(buy);
    assertEquals(3, buy.getItems().size());
  }

}