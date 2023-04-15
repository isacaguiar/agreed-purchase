package br.com.agreedpurchase.utils;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.model.Buy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JSONUtils {
  public static Buy toPurchaseResponse(String jsonData) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonData, Buy.class);
  }

  public static PixResponse toPixResponse(String jsonData) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonData, PixResponse.class);
  }

  public static PixRequest toPixRequest(String jsonData) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonData, PixRequest.class);
  }
}
