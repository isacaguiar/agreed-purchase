package br.com.agreedpurchase.adapter.gnet.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeRequest {

  private Calendario calendario;
  private Devedor devedor;
  private Valor valor;
  private String chave;
  private String solicitacaoPagador;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public class Calendario {
    private Long expiracao;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public class Devedor {
    private String cpf;
    private String nome;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public class Valor {
    private String original;
  }
}