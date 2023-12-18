import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.InMemoryArticleRepository;
import org.example.services.ArticleService;
import org.example.services.exceptions.ArticleDeleteException;
import org.example.services.exceptions.ArticleFindException;
import org.example.services.exceptions.ArticleUpdateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleServiceTest {
  InMemoryArticleRepository inMemoryArticleRepository = new InMemoryArticleRepository();
  ArticleService articleService = new ArticleService(inMemoryArticleRepository);
  String name = "first";
  Set<String> tags = Set.of("1", "article");
  List<Comment> comments = new ArrayList<>();
  Comment comment = new Comment(new CommentId(1), new ArticleId(1), "Hello!");
  ArticleId articleId = articleService.create(name, tags, comments);

  @Test
  void createTest() {
    Assertions.assertEquals(articleId, new ArticleId(1));
    Assertions.assertTrue(inMemoryArticleRepository.getArticlesMapSize() != 0);
  }

  @Test
  void findByIdTest() {
    Article article = articleService.findById(articleId);
    Assertions.assertEquals(articleId, article.getId());
    Assertions.assertEquals(name, article.getName());
    Assertions.assertEquals(tags, article.getTags());
    Assertions.assertEquals(comments, article.getComments());
  }

  @Test
  void updateTest() {
    comments.add(comment);
    articleService.update(articleId, name, tags, comments);
    Article article = articleService.findById(articleId);
    Assertions.assertEquals(article.getComments(), comments);
  }

  @Test
  void findAllTest() {
    List<Article> articles = articleService.findAll();
    Assertions.assertTrue(articles.size() == 1);
    assertEquals(articles.get(0).getName(), name);
    Assertions.assertEquals(articles.get(0).getTags(), tags);
    Assertions.assertEquals(articles.get(0).getComments(), comments);
  }

  @Test
  void deleteTest() {
    articleService.delete(articleId);
    assertEquals(0, inMemoryArticleRepository.getArticlesMapSize());
  }

  @Test
  void shouldThrowArticleUpdateException() {
    Assertions.assertThrows(ArticleUpdateException.class, () -> articleService.update(new ArticleId(15), " ", new HashSet<>(), List.of()));
  }

  @Test
  void shouldThrowArticleDeleteException() {
    Assertions.assertThrows(ArticleDeleteException.class, () -> articleService.delete(new ArticleId(10)));
  }

  @Test
  void shouldThrowArticleFindException() {
    Assertions.assertThrows(ArticleFindException.class, () -> articleService.findById(new ArticleId(10)));
  }
}
