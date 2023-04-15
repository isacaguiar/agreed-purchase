package br.com.agreedpurchase.adapter.controller;

import br.com.agreedpurchase.adapter.controller.request.BuyRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BuyController {

  @CrossOrigin
  @PostMapping
  ResponseEntity<?> buy(@Valid @RequestBody BuyRequest buyRequest);

}
