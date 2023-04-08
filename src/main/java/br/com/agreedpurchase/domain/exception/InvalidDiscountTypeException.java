package br.com.agreedpurchase.domain.exception;

public class InvalidDiscountTypeException extends RuntimeException {

  private String discountType;

  public InvalidDiscountTypeException(String discountType) {
    super();
    this.discountType = discountType;
  }
}
