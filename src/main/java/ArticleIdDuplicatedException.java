public class ArticleIdDuplicatedException extends RuntimeException{
  public ArticleIdDuplicatedException(String message) {
    super(message);
  }

  public ArticleIdDuplicatedException(String message, Throwable cause) {
    super(message, cause);
  }
}
