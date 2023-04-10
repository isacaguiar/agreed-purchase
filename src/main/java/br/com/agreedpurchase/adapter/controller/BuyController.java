package br.com.agreedpurchase.adapter.controller;

import br.com.agreedpurchase.adapter.controller.request.BuyRequest;
import com.sun.istack.NotNull;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/buy")
public interface BuyController {

  @PostMapping
  ResponseEntity<Object> buy(@Valid @RequestBody BuyRequest buyRequest);

  @GetMapping
  ResponseEntity<Object> load();

  @GetMapping(value = "/{id}")
  ResponseEntity<Object> load(@PathVariable("id")  Long id);

  @DeleteMapping(value = "/{id}")
  ResponseEntity<Object> delete(@NotNull @Valid @PathVariable Long id);

}
