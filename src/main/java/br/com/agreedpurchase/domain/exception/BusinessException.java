package br.com.agreedpurchase.domain.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private String message;

  public BusinessException(Throwable e) {
    super(e);
  }

  public BusinessException(String message, Throwable e) {
    super(e);
    this.message = message;
  }

  public BusinessException(String message) {
    super();
    this.message = message;
  }
}
