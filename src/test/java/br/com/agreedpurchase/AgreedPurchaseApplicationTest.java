package br.com.agreedpurchase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AgreedPurchaseApplicationTest {

  @Test
  void testConfigure() {
    String[] args = new String[]{};
    Runnable runnable = () -> AgreedPurchaseApplication.main(args);

    AgreedPurchaseApplication.main(args);

    assertNotNull(runnable);
  }

}