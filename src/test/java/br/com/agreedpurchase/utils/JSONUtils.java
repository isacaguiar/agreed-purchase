package br.com.agreedpurchase.utils;

import br.com.agreedpurchase.adapter.controller.response.PurchaseDataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JSONUtils {
  public static PurchaseDataResponse toPurchaseDataResponse(String jsonData) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonData, PurchaseDataResponse.class);
  }
}
