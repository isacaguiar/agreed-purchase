package br.com.agreedpurchase.domain.exception;

public class BusinessException extends RuntimeException {

  private final String message;

  public BusinessException(Throwable cause) {
    super(cause);
    this.message = cause.getMessage();
  }
  public BusinessException(String message) {
    super();
    this.message = message;
  }

}
