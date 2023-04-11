package br.com.agreedpurchase.adapter.gnet.client;

import br.com.agreedpurchase.adapter.client.FeignClientConfig;
import br.com.agreedpurchase.adapter.gnet.request.AuthorizeRequest;
import br.com.agreedpurchase.adapter.gnet.request.ChargeRequest;
import br.com.agreedpurchase.adapter.gnet.response.AuthorizeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    url = "${gnet.client.url}",
    name = "gnet-api-client",
    configuration = FeignClientConfig.class
)
public interface GerenciaNetApiClient {

  @PostMapping(path = "/oauth/token")
  AuthorizeResponse generateToken(
      @RequestHeader("Authorization") String authorization,
      @RequestBody AuthorizeRequest request);

  @PutMapping(path = "/v2/cob/{txid}")
  Object charge(
      @RequestHeader("Authorization") String authorization,
      @PathVariable("txid") String txid,
      @RequestBody ChargeRequest request);


}
