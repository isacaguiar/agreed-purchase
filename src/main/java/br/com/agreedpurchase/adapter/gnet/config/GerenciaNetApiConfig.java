package br.com.agreedpurchase.adapter.gnet.config;

import br.com.agreedpurchase.adapter.gnet.entity.GerenciaNetErrorException;
import br.com.agreedpurchase.adapter.gnet.entity.GerenciaNetErrorResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GerenciaNetApiConfig {
  @Bean
  public void config() {
    URL url = getClass().getClassLoader().getResource("certificado.p12");
    System.setProperty("javax.net.ssl.keyStore", url.getPath());
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return (methodKey, response) -> {
      GerenciaNetErrorException e = decode(response);
      if (e == null) {
        return FeignException.errorStatus(methodKey, response);
      }
      GerenciaNetErrorResponse error = e.getError();
      if (error == null) {
        return FeignException.errorStatus(methodKey, response);
      }
      return e;
    };
  }

  private GerenciaNetErrorException decode(Response response) {
    Reader reader = null;
    try {
      log.error("Error {}", response);
      reader = response.body().asReader();//.asReader(Charset.defaultCharset());
      String result = CharStreams.toString(reader);
      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      return mapper.readValue(result, GerenciaNetErrorException.class);
    } catch (Exception e) {
      log.error("Unexpected exception", e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}
