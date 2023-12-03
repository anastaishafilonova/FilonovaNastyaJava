package org.example.services.exceptions;

public class ArticleUpdateException extends RuntimeException{
  public ArticleUpdateException(String message, Throwable cause) {
    super(message, cause);
  }
}
