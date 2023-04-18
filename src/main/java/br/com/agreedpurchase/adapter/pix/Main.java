package br.com.agreedpurchase.adapter.pix;

import br.com.agreedpurchase.adapter.pix.vo.PixVO;
import java.math.BigDecimal;

public class Main {

  public static void main(String args[]) {
    PixVO pix = PixVO.builder()
        .txid("TEMP")
        .merchantName("Isac Aguiar")
        .merchantCity("Salvador")
        .description("Teste")
        .amount(new BigDecimal(10.00))
        .pixKey("isacaguiar@gmail.com")
        .build();
    System.out.println(pix.getPayloadFinal());
  }
}
