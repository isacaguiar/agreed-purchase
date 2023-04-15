package br.com.agreedpurchase.adapter.pix;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.adapter.pix.vo.PixVO;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.port.PixPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PixAdapter implements PixPort {

  @Override
  public PixResponse charge(PixRequest pixRequest) throws BusinessException {

    PixResponse pixResponse;
    try {
      PixVO pixVO = pixRequest.toVO();
      pixResponse = PixResponse.builder()
          .copyPaste(pixVO.getPayloadFinal())
          .build();
      log.info(pixVO.getPayloadFinal());
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    return pixResponse;
  }
}
