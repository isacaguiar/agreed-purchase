package br.com.agreedpurchase.adapter.pix.vo;

import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_ADDITIONAL_DATA_FIELD_TEMPLATE;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_ADDITIONAL_DATA_FIELD_TEMPLATE_TXID;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_COUNTRY_CODE;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_CRC16;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_MERCHANT_ACCOUNT_INFORMATION;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_MERCHANT_ACCOUNT_INFORMATION_GUI;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_MERCHANT_ACCOUNT_INFORMATION_KEY;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_MERCHANT_CATEGORY_CODE;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_MERCHANT_CITY;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_MERCHANT_NAME;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_PAYLOAD_FORMAT_INDICATOR;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_TRANSACTION_AMOUNT;
import static br.com.agreedpurchase.adapter.pix.utils.PixKeyConstants.ID_TRANSACTION_CURRENCY;

import br.com.agreedpurchase.domain.exception.BusinessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PixVO {
  private String pixKey;
  private String description;
  private String merchantName;
  private String merchantCity;
  private String txid;
  private BigDecimal amount;
  private static final String FORMAT_ID = "01";
  private static final String MERCHANT_ACCOUNT_INFORMATION_GUI = "br.gov.bcb.pix";
  private static final String MERCANT_CATEGORY_CODE = "0000";

  public void setAmount(BigDecimal amount) {
    this.amount = ((amount == null)
        ? new BigDecimal("0.00") : amount.setScale(2, RoundingMode.HALF_EVEN));
  }

  /**
   * Método respons[avel por retornar valores da conta.
   */
  public String getMerchantAccountInformation() throws BusinessException {
    //Domínio do banco
    String gui = getValue(ID_MERCHANT_ACCOUNT_INFORMATION_GUI, MERCHANT_ACCOUNT_INFORMATION_GUI);
    //Chave Pix
    String key = getValue(ID_MERCHANT_ACCOUNT_INFORMATION_KEY, pixKey);
    //Descrição do pagamento
    String desc = "";
    if (description != null && !"".equals(description)) {
      desc = getValue(ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION, description);
    }

    //Valor completo da conta
    return getValue(ID_MERCHANT_ACCOUNT_INFORMATION, gui.concat(key).concat(desc));
  }

  /**
   * Método responsável por retornar os valores do campo adicional.
   */
  public String getAdditionalDataFieldTemplate() throws BusinessException {
    //TXID
    String txidValue = getValue(ID_ADDITIONAL_DATA_FIELD_TEMPLATE_TXID, txid);

    //Valor completo da conta
    return getValue(ID_ADDITIONAL_DATA_FIELD_TEMPLATE, txidValue);
  }

  /**
   * Método responsável por retornar o payload final com o CRC16.
   */
  public String getPayloadFinal()  {
    String payload = getPayload();
    String crc16 = getCRC16();
    return payload.concat(String.valueOf(crc16));
  }

  /**
   * Método que retorna o payload sem o CRC16.
   */
  public String getPayload() throws BusinessException {
    StringBuilder payload = new StringBuilder()
        .append(getValue(ID_PAYLOAD_FORMAT_INDICATOR, FORMAT_ID))
        .append(getMerchantAccountInformation())

        .append(getValue(ID_MERCHANT_CATEGORY_CODE, MERCANT_CATEGORY_CODE))
        .append(getValue(ID_TRANSACTION_CURRENCY, "986"))
        .append(getValue(ID_TRANSACTION_AMOUNT, String.valueOf(amount.setScale(2, RoundingMode.HALF_EVEN))))

        .append(getValue(ID_COUNTRY_CODE, "BR"))
        .append(getValue(ID_MERCHANT_NAME, merchantName))
        .append(getValue(ID_MERCHANT_CITY, merchantCity))
        .append(getAdditionalDataFieldTemplate())
        .append(ID_CRC16.concat("04"));
    return payload.toString();
  }

  @Override
  public String toString() {
    String value = "PixPayload{ \"pixKey\" : \"" + pixKey + "\", \"description\" : \"" + description + "\", "
        .concat("\"merchantName\" : \"" + merchantName + "\", \"merchantCity\" : \"" + merchantCity + "\",")
        .concat("\"txid\" : \"" + txid + "\", \"amount\" : \"" + amount + "\"}");
    return value;
  }

  /**
   * Retorna o valor para montagem payload.
   */
  public String getValue(String id, String value) {
    try {
      String aux = String.format("%2s", value.length()).replace(' ', '0');
      return id.concat(aux).concat(value);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  /**
   * Método que retorna o CRC16.
   */
  public String getCRC16() throws BusinessException {
    return String.valueOf(calculateCRC16(getPayload()));
  }

  /**
   * Método que calcula o CRC16.
   */
  public String calculateCRC16(String pix) {
    //Dados definidos pelo BACEN
    int crc = 0xFFFF; // valor inicial do CRC16
    int polynomial = 0x1021; // polinômio usado para calcular o CRC16

    byte[] bytes = pix.getBytes(); //converte a string para um array de bytes

    for (byte b : bytes) {
      for (int i = 0; i < 8; i++) {
        boolean bit = ((b >> (7 - i) & 1) == 1);
        boolean c15 = ((crc >> 15 & 1) == 1);
        crc <<= 1;
        if (c15 ^ bit) {
          crc ^= polynomial;
        }
      }
    }

    crc &= 0xffff;
    return Integer.toHexString(crc).toUpperCase();
  }

}
