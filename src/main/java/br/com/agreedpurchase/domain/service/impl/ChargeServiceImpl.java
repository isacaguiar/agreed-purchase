package br.com.agreedpurchase.domain.service.impl;

import br.com.agreedpurchase.adapter.gnet.response.AuthorizeResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.port.GerenciaNetPort;
import br.com.agreedpurchase.domain.service.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChargeServiceImpl implements ChargeService {

  @Autowired
  GerenciaNetPort gerenciaNetPort;

  public void charge() throws BusinessException {
    AuthorizeResponse authorizeResponse = gerenciaNetPort.generateToken();



  }
}
