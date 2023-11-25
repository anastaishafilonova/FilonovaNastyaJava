import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryArticleRepository implements ArticleRepository{

  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<ArticleId, Article> articlesMap = new ConcurrentHashMap<>();

  @Override
  public ArticleId generateId() {
    return new ArticleId(nextId.incrementAndGet());
  }

  @Override
  public List<Article> findAll() {
    return new ArrayList<>(articlesMap.values());
  }

  @Override
  public Article findById(ArticleId articleId) {
    Article article = articlesMap.get(articleId);
    if (article == null) {
      throw new ArticleNotFoundException("Cannot find book by id=" + articleId);
    }
    return article;
  }

  @Override
  public synchronized void create(Article article) {
    if (articlesMap.get(article.getId()) != null) {
      throw new ArticleIdDuplicatedException("Book with the given id already exists: " + article.getId());
    }
    articlesMap.put(article.getId(), article);
  }

  @Override
  public synchronized void update(Article article) {
    if (articlesMap.get(article.getId()) == null) {
      throw new ArticleNotFoundException("Cannot find book by id=" + article.getId());
    }
    articlesMap.put(article.getId(), article);
  }

  @Override
  public void delete(ArticleId articleId) {
    if (articlesMap.remove(articleId) == null) {
      throw new ArticleNotFoundException("Cannot find book by id=" + articleId);
    }
  }
}