package br.com.agreedpurchase.domain.port;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.agreedpurchase.adapter.pix.PixAdapter;
import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.utils.BuilderUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PixPort.class, PixAdapter.class})
class PixPortTest {

  @Autowired
  PixPort pixPort;

  @Test
  void charge() {

    PixRequest request = BuilderUtils.loadPixRequest();
    String key = "598745154569548743";

    PixResponse pixResponse = pixPort.charge(request);

    assertNotNull(pixResponse);
    assertNotNull(pixResponse.getCopyPaste());
  }

  @Test
  void chargeWhenException() {
    PixRequest request = mock(PixRequest.class);

    when(request.toVO()).thenThrow(BusinessException.class);

    assertThrows(BusinessException.class, () -> {
      pixPort.charge(request);
    });
  }

}