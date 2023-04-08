package br.com.agreedpurchase.domain.port;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;

public interface PersistencePort {
  BuyEntity buy(BuyEntity buyEntity);
}
