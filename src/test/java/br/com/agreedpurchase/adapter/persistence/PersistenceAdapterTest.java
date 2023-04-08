package br.com.agreedpurchase.adapter.persistence;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.repository.BuyRepository;
import br.com.agreedpurchase.utils.BuilderUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest({PersistenceAdapter.class})
@AutoConfigureMockMvc
class PersistenceAdapterTest {

  @Autowired
  PersistenceAdapter persistenceAdapter;

  @MockBean
  BuyRepository buyRepository;
  @Test
  void buy() {
    BigDecimal fee = new BigDecimal(10);
    BigDecimal discount = new BigDecimal(6);
    BuyEntity buyEntity = BuilderUtils.getBuyEntityWithItems(fee, discount,"DELIVERY");

    when(buyRepository.save(buyEntity)).thenReturn(buyEntity);

    persistenceAdapter.buy(buyEntity);

    verify(buyRepository).save(buyEntity);
  }

}