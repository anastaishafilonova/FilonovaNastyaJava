package org.example.services.exceptions;

public class ArticleFindException extends RuntimeException {

  public ArticleFindException(String message, Throwable cause) {
    super(message, cause);
  }
}
