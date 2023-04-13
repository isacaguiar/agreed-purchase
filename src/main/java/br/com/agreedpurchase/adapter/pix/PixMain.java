package br.com.agreedpurchase.adapter.pix;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.vo.PixVO;
import java.math.BigDecimal;
import org.springframework.beans.BeanUtils;

public class PixMain {

  public static void main(String args[]) throws Exception {
    PixRequest payload = PixRequest.builder()
        .pixKey("isacaguiar@gmail.com")
        .description("QR Code Teste")
        .merchantName("Isac Aguiar") //Limitar tamanho máximo de 25
        .merchantCity("Salvador")
        .amount(new BigDecimal(100.00))
        .txid("WDEV1234")
        .build();

    PixVO pixVO = new PixVO();

    BeanUtils.copyProperties(payload, pixVO);

    System.out.println(pixVO);
    System.out.println("------------------------------");
    System.out.println(pixVO.getPayload());
    System.out.println("------------------------------");
    System.out.println(pixVO.getPayloadFinal());

  }
}
