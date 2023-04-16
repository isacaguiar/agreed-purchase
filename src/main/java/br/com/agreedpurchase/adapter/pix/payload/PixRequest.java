package br.com.agreedpurchase.adapter.pix.payload;

import br.com.agreedpurchase.adapter.pix.vo.PixVO;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PixRequest {
  private String pixKey;
  private String description;
  private String merchantName;
  private String merchantCity;
  private String txid;
  private BigDecimal amount;

  @Override
  public String toString() {
    String value = "{ \"pixKey\" : \"" + pixKey + "\", \"description\" : \"" + description + "\", "
        .concat("\"merchantName\" : \"" + merchantName + "\", \"merchantCity\" : \"" + merchantCity + "\",")
        .concat("\"txid\" : \"" + txid + "\", \"amount\" : \"" + amount + "\"}");
    return value;
  }

  public PixVO toVO() {
    return PixVO.builder()
        .pixKey(pixKey)
        .amount(amount)
        .description(description)
        .merchantCity(merchantCity)
        .merchantName(merchantName)
        .txid(txid)
        .build();
  }
}
