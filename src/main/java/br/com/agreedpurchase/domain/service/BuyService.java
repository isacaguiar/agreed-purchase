package br.com.agreedpurchase.domain.service;

import br.com.agreedpurchase.domain.exception.InvalidDiscountTypeException;
import br.com.agreedpurchase.domain.model.Buy;

public interface BuyService {
  Buy buy(Buy buy) throws InvalidDiscountTypeException;
}
