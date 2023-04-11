package br.com.agreedpurchase.adapter.gnet.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorizeRequest {
  @JsonProperty("grant_type")
  private String grantType;

}
