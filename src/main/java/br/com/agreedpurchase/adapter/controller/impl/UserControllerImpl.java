package br.com.agreedpurchase.adapter.controller.impl;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import br.com.agreedpurchase.adapter.controller.UserController;
import br.com.agreedpurchase.adapter.controller.request.BuyRequest;
import br.com.agreedpurchase.adapter.controller.request.UserRequest;
import br.com.agreedpurchase.adapter.controller.response.PurchaseDataResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.User;
import br.com.agreedpurchase.domain.service.UserService;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserControllerImpl implements UserController {

  @Autowired
  UserService userService;



  public ResponseEntity<Object> login(@Valid @RequestBody UserRequest userRequest) {
    log.info("Process login initialized");
    String token;
    try {
      token = userService.login(userRequest.toModel());
      log.info("Login successful");
    } catch (BusinessException e) {
      log.error("Error on login");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok(token);
  }

  public ResponseEntity<Object> register(@Valid @RequestBody UserRequest userRequest) {
    log.info("Process register initialized");
    User user;
    try {
      user = userService.register(userRequest.toModel());
      log.info("Register successful");
    } catch (BusinessException e) {
      log.error("Error on register");
      return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
    }
    return ResponseEntity.ok("User created successful");
  }

}
