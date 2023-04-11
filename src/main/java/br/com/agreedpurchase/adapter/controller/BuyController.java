package br.com.agreedpurchase.adapter.controller;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import br.com.agreedpurchase.adapter.controller.request.BuyRequest;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.BuyService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/buy")
public class BuyController {

  @Autowired
  BuyService buyService;

  @PostMapping
  public ResponseEntity<Object> buy(@Valid @RequestBody BuyRequest buyRequest) {
    log.info("Purchase process  initialized");
    Buy buy;
    try {
      buy = buyService.buy(buyRequest.toModel());
      log.info("Successful purchase");
    } catch (BusinessException e) {
      log.error("Error on purchase");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(buy);
  }

}
