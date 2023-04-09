package br.com.agreedpurchase.adapter.controller;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import br.com.agreedpurchase.adapter.controller.request.BuyRequest;
import br.com.agreedpurchase.adapter.controller.response.PurchaseDataResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.BuyService;
import com.sun.istack.NotNull;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    log.info("Process buy initialized");
    Buy buy;
    try {
      buy = buyService.buy(buyRequest.toModel());
      log.info("Successful purchase");
    } catch (BusinessException e) {
      log.error("Error on purchase");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(new PurchaseDataResponse(buy));
  }

  @GetMapping
  public ResponseEntity<Object> load() {
    log.info("Load process initialized");
    List<Buy> buys;
    try {
      buys = buyService.loadAll();
      log.info("Successful load");
    } catch (BusinessException e) {
      log.error("Error on load");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(buys);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> load(@PathVariable("id")  Long id) {
    log.info("Load process initialized");
    Buy buy;
    try {
      buy = buyService.loadById(id);
      log.info("Successful load");
    } catch (BusinessException e) {
      log.error("Error on load");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(buy);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> delete(@NotNull @Valid @PathVariable Long id) {
    log.info("Delete buy register initialized");
    try {
      buyService.delete(id);
      log.info("Successful Delete buy register");
    } catch (BusinessException e) {
      log.error("Error on delete");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(null);
  }

}
