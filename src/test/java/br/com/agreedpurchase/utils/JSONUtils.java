package br.com.agreedpurchase.utils;

import br.com.agreedpurchase.domain.model.Buy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JSONUtils {
  public static Buy toPurchaseDataResponse(String jsonData) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonData, Buy.class);
  }
}
