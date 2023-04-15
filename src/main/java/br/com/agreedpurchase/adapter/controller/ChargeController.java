package br.com.agreedpurchase.adapter.controller;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface ChargeController {

  @CrossOrigin
  @PostMapping
  ResponseEntity<?> charge(@Valid @RequestBody PixRequest pixRequest);
}
