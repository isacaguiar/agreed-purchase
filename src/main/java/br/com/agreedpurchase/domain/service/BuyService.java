package br.com.agreedpurchase.domain.service;

import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import java.util.List;

public interface BuyService {
  Buy buy(Buy buy) throws BusinessException;

  Buy loadById(Long id) throws BusinessException;

  List<Buy> loadAll() throws BusinessException;

  void delete(Long id) throws BusinessException;
}
