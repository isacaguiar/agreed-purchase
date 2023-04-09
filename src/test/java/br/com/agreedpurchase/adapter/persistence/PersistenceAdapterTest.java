package br.com.agreedpurchase.adapter.persistence;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.DELIVERY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.repository.BuyRepository;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.utils.BuilderUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
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
    assertNotNull(buyEntity.getId());
    assertNotNull(buyEntity.getDate());
    assertEquals(fee, buyEntity.getFee());
    assertEquals(discount, buyEntity.getDiscount());
    assertEquals("DELIVERY", buyEntity.getDiscountType());
    assertNotNull(buyEntity.getItemEntities());
    assertTrue(buyEntity.getItemEntities().size() > 0);
  }

  @Test
  void getBuyEntityById() {
    Long id = 1L;
    BigDecimal fee = new BigDecimal(10);
    BigDecimal discount = new BigDecimal(6);
    Optional<BuyEntity> optionalBuyEntity = Optional.of(BuilderUtils.getBuyEntityWithItems(fee, discount, DELIVERY));
    when(buyRepository.findById(id)).thenReturn(optionalBuyEntity);
    optionalBuyEntity = persistenceAdapter.getBuyEntityById(id);

    verify(buyRepository).findById(id);
    assertTrue(optionalBuyEntity.isPresent());
  }

  @Test
  void getBuyEntityByIdWhenNotFound() {
    Long id = 1L;

    when(buyRepository.findById(any())).thenThrow(BusinessException.class);

    assertThrows(BusinessException.class, () -> {
      persistenceAdapter.getBuyEntityById(id);
    });

    verify(buyRepository).findById(id);
  }

  @Test
  void getBuyEntities() {
    Sort sort = Sort.by(Sort.Direction.DESC, "date");
    when(buyRepository.findAll(sort)).thenReturn(new ArrayList<>());
    persistenceAdapter.getBuyEntities();

    verify(buyRepository).findAll(sort);
  }
  @Test
  void deleteBuyEntityById() {
    Long id = 1L;
    buyRepository.deleteById(id);
    verify(buyRepository).deleteById(id);
  }

}