package br.com.agreedpurchase.adapter.gnet.client;

import br.com.agreedpurchase.adapter.gnet.request.AuthorizeRequest;
import br.com.agreedpurchase.adapter.gnet.request.ChargeRequest;
import br.com.agreedpurchase.adapter.gnet.response.AuthorizeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GerenciaNetApiClientWrapper {

  @Value("${gnet.client.id}")
  private String clientId;

  @Value("${gnet.client.secret}")
  private String clientSecret;

  @Autowired
  GerenciaNetApiClient client;

  public AuthorizeResponse generateCharge() {

    String authStr = clientId+":"+clientSecret;
    String base64Creds = Base64.getEncoder().encodeToString(((authStr).getBytes()));

    AuthorizeRequest authorizeRequest =
        AuthorizeRequest.builder()
            .grantType("client_credentials")
            .build();

    AuthorizeResponse authorizeResponse =
        client.generateToken("Basic ".concat(base64Creds), authorizeRequest);

    String payload;
    payload = "{\"calendario\": {\"expiracao\": 3600},\"devedor\": {\"cpf\": \"12345678909\",\"nome\": \"Francisco da Silva\"},\"valor\": {\"original\": \"123.45\"},\"chave\": \"71cdf9ba-c695-4e3c-b010-abb521a3f1be\",\"solicitacaoPagador\": \"Cobrança dos serviços prestados.\"}";

    ChargeRequest chargeRequest = null;
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      chargeRequest = objectMapper.readValue(payload, ChargeRequest.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object object = client.charge("Bearer ".concat(authorizeResponse.getAccessToken()), chargeRequest);

    return authorizeResponse;
  }
}
