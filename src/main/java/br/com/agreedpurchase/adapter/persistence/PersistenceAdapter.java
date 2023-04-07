package br.com.agreedpurchase.adapter.persistence;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.repository.BuyRepository;
import br.com.agreedpurchase.domain.model.PurcharseData;
import br.com.agreedpurchase.domain.port.PersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceAdapter implements PersistencePort {

  @Autowired
  BuyRepository buyRepository;

  public BuyEntity buy(BuyEntity buyEntity) {
    return buyRepository.save(buyEntity);
  }
}
