package br.com.agreedpurchase.domain.service.impl;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.port.PixPort;
import br.com.agreedpurchase.domain.service.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChargeServiceImpl implements ChargeService {

  @Autowired
  PixPort pixPort;

  public PixResponse charge(PixRequest pixRequest) throws BusinessException {
    return pixPort.charge(pixRequest);
  }
}
