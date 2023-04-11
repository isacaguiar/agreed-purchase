package br.com.agreedpurchase.adapter.gnet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GerenciaNetErrorException extends RuntimeException {
  private final GerenciaNetErrorResponse error;

  @Override
  public String getMessage() {
    return error != null ? error.getNome() + ": " + error.getMensagem() : "unknown";
  }
}
