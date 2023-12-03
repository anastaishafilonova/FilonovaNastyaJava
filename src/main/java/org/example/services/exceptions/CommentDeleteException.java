package org.example.services.exceptions;

public class CommentDeleteException extends RuntimeException{
  public CommentDeleteException(String message, Throwable cause) {
    super(message, cause);
  }
}
