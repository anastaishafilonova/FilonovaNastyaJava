import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.ArticleRepository;
import org.example.repositories.InMemoryArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryArticleRepositoryTest {
  InMemoryArticleRepository articleRepository = new InMemoryArticleRepository();
  ArticleId articleId = new ArticleId(1);
  String name = "first";
  Set<String> tags = Set.of("1", "article");
  List<Comment> comments = new ArrayList<>();
  Comment comment = new Comment(new CommentId(1), new ArticleId(1), "Hello!");
  @Test
  public void generateIdTest() {
    ArticleId id1 = articleRepository.generateId();
    ArticleId id2 = articleRepository.generateId();
    ArticleId id3 = articleRepository.generateId();
    Assertions.assertEquals(new ArticleId(1), id1);
    Assertions.assertEquals(new ArticleId(2), id2);
    Assertions.assertEquals(new ArticleId(3), id3);
  }

  @Test
  void getArticlesMapSizeTest() {
    Assertions.assertEquals(0, articleRepository.getArticlesMapSize());
  }

  @Test
  void createTest() {
    articleRepository.create(new Article(articleId, name, tags, comments));
    Assertions.assertEquals(articleId, new ArticleId(1));
    Assertions.assertTrue(articleRepository.getArticlesMapSize() != 0);
  }

  @Test
  void findByIdTest() {
    articleRepository.create(new Article(articleId, name, tags, comments));
    Article article = articleRepository.findById(articleId);
    Assertions.assertEquals(articleId, article.getId());
    Assertions.assertEquals(name, article.getName());
    Assertions.assertEquals(tags, article.getTags());
    Assertions.assertEquals(comments, article.getComments());
  }

  @Test
  void updateTest() {
    articleRepository.create(new Article(articleId, name, tags, comments));
    comments.add(comment);
    articleRepository.update(new Article(articleId, name, tags, comments));
    Article article = articleRepository.findById(articleId);
    Assertions.assertEquals(article.getComments(), comments);
  }

  @Test
  void findAllTest() {
    articleRepository.create(new Article(articleId, name, tags, comments));
    List<Article> articles = articleRepository.findAll();
    Assertions.assertTrue(articles.size() == 1);
    assertEquals(articles.get(0).getName(), name);
    Assertions.assertEquals(articles.get(0).getTags(), tags);
    Assertions.assertEquals(articles.get(0).getComments(), comments);
  }

  @Test
  void deleteTest() {
    articleRepository.create(new Article(articleId, name, tags, comments));
    articleRepository.delete(articleId);
    assertEquals(0, articleRepository.getArticlesMapSize());
  }
}
