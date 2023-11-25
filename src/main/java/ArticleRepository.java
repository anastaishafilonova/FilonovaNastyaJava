import java.util.*;

public interface ArticleRepository {

  ArticleId generateId();

  List<Article> findAll();

  /**
   * @throws ArticleNotFoundException
   */
  Article findById(ArticleId articleId);

  /**
   * @throws ArticleIdDuplicatedException
   */
  void create(Article article);

  /**
   * @throws ArticleNotFoundException
   */
  void update(Article article);

  /**
   * @throws ArticleNotFoundException
   */
  void delete(ArticleId articleId);

}