package br.com.agreedpurchase.adapter.pix;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.adapter.pix.vo.PixVO;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.port.PixPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class PixAdapter implements PixPort {

  @Override
  public PixResponse charge(PixRequest pixRequest) throws BusinessException {
    /*PixRequest payload = PixRequest.builder()
        .pixKey("isacaguiar@gmail.com")
        .description("QR Code Teste")
        .merchantName("Isac Aguiar") //Limitar tamanho máximo de 25
        .merchantCity("Salvador")
        .amount(new BigDecimal(100.00))
        .txid("WDEV1234")
        .build();*/

    PixVO pixVO = new PixVO();

    BeanUtils.copyProperties(pixRequest, pixVO);

    PixResponse pixResponse;
    try {
      pixResponse = PixResponse.builder()
          .key(pixVO.getPayloadFinal())
          .build();
      log.info(pixVO.getPayloadFinal());
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    return pixResponse;
  }
}
