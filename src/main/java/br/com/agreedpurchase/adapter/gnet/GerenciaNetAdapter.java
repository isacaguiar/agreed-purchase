package br.com.agreedpurchase.adapter.gnet;

import br.com.agreedpurchase.adapter.gnet.client.GerenciaNetApiClient;
import br.com.agreedpurchase.adapter.gnet.client.GerenciaNetApiClientWrapper;
import br.com.agreedpurchase.adapter.gnet.request.AuthorizeRequest;
import br.com.agreedpurchase.adapter.gnet.response.AuthorizeResponse;
import br.com.agreedpurchase.domain.port.GerenciaNetPort;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GerenciaNetAdapter implements GerenciaNetPort {

  @Value("${gnet.client.id}")
  private String clientId;

  @Value("${gnet.client.secret}")
  private String clientSecret;

  @Autowired
  GerenciaNetApiClientWrapper gerenciaNetApiClient;


  public AuthorizeResponse generateToken() {

    gerenciaNetApiClient.generateToken();



    return null;
  }
}
