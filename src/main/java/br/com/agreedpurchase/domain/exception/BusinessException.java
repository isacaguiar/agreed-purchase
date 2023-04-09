package br.com.agreedpurchase.domain.exception;

public class BusinessException extends RuntimeException {

  private final String message;

  public BusinessException(String message) {
    super();
    this.message = message;
  }

}
