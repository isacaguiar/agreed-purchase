package br.com.agreedpurchase.adapter.controller;

import br.com.agreedpurchase.adapter.controller.request.ChargeRequest;
import br.com.agreedpurchase.adapter.controller.request.UserRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/")
public interface UserController {

  @PutMapping(value = "login")
  ResponseEntity<Object> login(@Valid @RequestBody UserRequest userRequest);

  @PutMapping(value = "register")
  ResponseEntity<Object> register(@Valid @RequestBody UserRequest userRequest);

}
