package br.com.agreedpurchase.adapter.controller.impl;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import br.com.agreedpurchase.adapter.controller.ChargeController;
import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.service.ChargeService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/charge")
public class ChargeControllerImpl implements ChargeController {

  @Autowired
  ChargeService chargeService;

  public ResponseEntity<?> charge(@Valid @RequestBody PixRequest pixRequest) {
    log.info("Purchase process  initialized");
    PixResponse pixResponse = PixResponse.builder().build();
    try {
      pixResponse = chargeService.charge(pixRequest);
      log.info("Successful purchase");
    } catch (BusinessException e) {
      log.error("Error on purchase");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(pixResponse);
  }
}
