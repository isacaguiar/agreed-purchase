package br.com.agreedpurchase.domain.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.port.PixPort;
import br.com.agreedpurchase.domain.service.impl.ChargeServiceImpl;
import br.com.agreedpurchase.utils.BuilderUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ChargeService.class, ChargeServiceImpl.class})
class ChargeServiceTest {

  @Autowired
  ChargeService chargeService;

  @MockBean
  PixPort pixPort;

  @Test
  void shouldSuccessWhenExecuteBuy() {

    PixResponse pix = BuilderUtils.getPixResponse("CÒDIGO");

    when(pixPort.charge(any())).thenReturn(pix);

    PixResponse pixResponse =
        chargeService.charge(BuilderUtils.loadPixRequest());

    assertNotNull(pixResponse);
  }

}