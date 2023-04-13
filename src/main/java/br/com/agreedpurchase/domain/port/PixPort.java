package br.com.agreedpurchase.domain.port;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;

public interface PixPort {

  PixResponse charge(PixRequest pixRequest) throws BusinessException;
}
