package org.example.services.exceptions;

public class ArticleCreateException extends RuntimeException {
  public ArticleCreateException(String message) {
    super(message);
  }

  public ArticleCreateException(String message, Throwable cause) {
    super(message, cause);
  }
}
