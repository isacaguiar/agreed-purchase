package br.com.agreedpurchase.domain.exception;

public class SystemException extends Exception {
  private String message;

  public SystemException(Throwable e) {
    super(e);
  }

  public SystemException(String messagem) {
    super();
    this.message = messagem;
  }

  public SystemException(String messagem, Throwable e) {
    super(e);
    this.message = messagem;
  }
}
