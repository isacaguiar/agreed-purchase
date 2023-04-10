package br.com.agreedpurchase.adapter.controller;

import br.com.agreedpurchase.adapter.controller.request.ChargeRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/charge")
public interface ChargeController {

  @PutMapping
  ResponseEntity<Object> charge(@Valid @RequestBody ChargeRequest chargeRequest);

}
