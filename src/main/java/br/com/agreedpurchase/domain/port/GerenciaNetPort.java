package br.com.agreedpurchase.domain.port;

import br.com.agreedpurchase.adapter.gnet.response.AuthorizeResponse;

public interface GerenciaNetPort {

  AuthorizeResponse generateToken();
}
