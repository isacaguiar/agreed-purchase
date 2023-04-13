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
public class PixVO {
  private String pixKey;
  private String description;
  private String merchantName;
  private String merchantCity;
  private String txid;
  private BigDecimal amount;
  private final String formatId = "01";
  private final String merchantAccountInformationGui = "br.gov.bcb.pix";
  private final String merchantCategoryCode = "0000";

  public void setAmount(BigDecimal amount) {
    this.amount = amount == null ?
        new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN) :
        amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
  }

  /**
   * Método respons[avel por retornar valores da conta
   *
   * @return
   */
  public String getMerchantAccountInformation() throws Exception {
    //Domínio do banco
    String gui = getValue(ID_MERCHANT_ACCOUNT_INFORMATION_GUI, merchantAccountInformationGui);
    //Chave Pix
    String key = getValue(ID_MERCHANT_ACCOUNT_INFORMATION_KEY, pixKey);
    //Descrição do pagamento
    String desc = "";
    if (description !=null && !"".equals(description)) {
      desc = getValue(ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION, description);
    }

    //Valor completo da conta
    return getValue(ID_MERCHANT_ACCOUNT_INFORMATION, gui.concat(key).concat(desc));
  }

  /**
   * Método responsável por retornar os valores do campo adicional
   *
   * @return
   */
  public String getAdditionalDataFieldTemplate() throws Exception {
    //TXID
    String txidValue = getValue(ID_ADDITIONAL_DATA_FIELD_TEMPLATE_TXID, txid);

    //Valor completo da conta
    return getValue(ID_ADDITIONAL_DATA_FIELD_TEMPLATE, txidValue);
  }

  public String getPayloadFinal() throws Exception {
    String payload = getPayload();
    String crc16 = getCRC16();
    return payload.concat(String.valueOf(crc16));
  }

  public String getPayload() throws Exception {
    StringBuilder payload = new StringBuilder()
        .append(getValue(ID_PAYLOAD_FORMAT_INDICATOR, formatId))
        .append(getMerchantAccountInformation())

        .append(getValue(ID_MERCHANT_CATEGORY_CODE, merchantCategoryCode))
        .append(getValue(ID_TRANSACTION_CURRENCY, "986"))
        .append(getValue(ID_TRANSACTION_AMOUNT, String.valueOf(amount.setScale(2, BigDecimal.ROUND_HALF_EVEN))))

        .append(getValue(ID_COUNTRY_CODE, "BR"))
        .append(getValue(ID_MERCHANT_NAME, merchantName))
        .append(getValue(ID_MERCHANT_CITY, merchantCity))
        .append(getAdditionalDataFieldTemplate())
        .append(ID_CRC16.concat("04"));
    return payload.toString();
  }

  @Override
  public String toString() {
    return "PixPayload{ \"pixKey\" : \"" + pixKey + "\", \"description\" : \"" + description +
        "\", " +
        "\"merchantName\" : \"" + merchantName + "\", \"merchantCity\" : \"" + merchantCity +
        "\"," +
        "\"txid\" : \"" + txid + "\", \"amount\" : \"" + amount + "\"}";
  }

  /**
   * Retorna o valor do payload
   *
   * @param id
   * @param value
   * @return
   */
  public String getValue(String id, String value) {
    try {
      String aux = String.format("%2s", value.length()).replace(' ', '0');
      return id.concat(aux).concat(value);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public String getCRC16() throws Exception {
    //System.out.println("Hard 1: "+calculateCRC16("00020126640014br.gov.bcb.pix011112123456789000226Pagamento do pedido 1234565204000053039865403100.005802BR5913Willian Costa6009SAO PAULO62120508WDEV12346304"));
    //System.out.println("Hard 2: "+calculateCRC16( "00020126630014br.gov.bcb.pix0111123456789000226Pagamento do pedido 1234565204000053039865406100.005802BR5913Willian Costa6009SAO PAULO62120508WDEV1234"));


    return String.valueOf(calculateCRC16(getPayload()));
  }

  public String calculateCRC16(String pix) {
    //Dados definidos pelo BACEN
    int crc = 0xFFFF; // valor inicial do CRC16
    int polynomial = 0x1021; // polinômio usado para calcular o CRC16

    byte[] bytes = pix.getBytes(); // converte a string para um array de bytes

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

    crc &= 0xffff; // faz um AND para garantir que o valor seja de 16 bits

//    System.out.println("PIX: "+pix);
//    System.out.printf("%04X\n", crc);
//    int crcDecimal = Integer.parseInt(String.valueOf(crc), 16);
//    System.out.println(crcDecimal);
//    System.out.println("CRC16-CCITT = " + Integer.toHexString(crc));
    return Integer.toHexString(crc).toUpperCase();
  }

}