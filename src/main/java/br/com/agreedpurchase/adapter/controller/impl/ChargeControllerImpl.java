package br.com.agreedpurchase.adapter.controller.impl;

import br.com.agreedpurchase.adapter.controller.ChargeController;
import br.com.agreedpurchase.adapter.controller.request.BuyRequest;
import br.com.agreedpurchase.adapter.controller.request.ChargeRequest;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.ChargeService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ChargeControllerImpl implements ChargeController {

  @Autowired
  ChargeService chargeService;

  @PutMapping
  public ResponseEntity<Object> charge(@Valid @RequestBody ChargeRequest chargeRequest) {
    log.info("Process charge initialized");
    /*try {
      buy = buyService.buy(buyRequest.toModel());
      log.info("Successful purchase");
    } catch (BusinessException e) {
      log.error("Error on purchase");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }*/
    return ResponseEntity.ok("OK");
  }
}
