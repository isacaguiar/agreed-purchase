package br.com.agreedpurchase.domain.port;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import java.util.List;
import java.util.Optional;

public interface PersistencePort {
  BuyEntity buy(BuyEntity buyEntity);

  Optional<BuyEntity> getBuyEntityById(Long id);

  List<BuyEntity> getBuyEntities();

  void deleteBuyEntityById(Long id);
}
