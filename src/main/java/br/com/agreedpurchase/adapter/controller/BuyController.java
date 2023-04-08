package br.com.agreedpurchase.adapter.controller;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import br.com.agreedpurchase.adapter.controller.request.BuyRequest;
import br.com.agreedpurchase.adapter.controller.response.PurchaseDataResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.exception.InvalidDiscountTypeException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.BuyService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/buy")
public class BuyController {

  @Autowired
  BuyService buyService;

  @PostMapping
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<Object> buy(@Valid @RequestBody BuyRequest buyRequest) {

    log.info("Process buy initialized");
    Buy buy = null;
    try {
      buy = buyService.buy(buyRequest.toModel());
    } catch (BusinessException e) {
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }

    log.info("Successful purchase");

    return ResponseEntity.ok(new PurchaseDataResponse(buy));
  }

}
