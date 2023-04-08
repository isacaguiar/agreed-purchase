package br.com.agreedpurchase.domain.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException {

  private final String message;

  public BusinessException(Throwable e, String message) {
    super(e);
    this.message = message;
  }

  public BusinessException(String message) {
    super();
    this.message = message;
  }

}
