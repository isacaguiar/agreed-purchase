package br.com.agreedpurchase.adapter.controller;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ChargeController {

  @CrossOrigin(origins = "http://localhost:8080")
  @PostMapping
  ResponseEntity<Object> charge(@Valid @RequestBody PixRequest pixRequest);
}
