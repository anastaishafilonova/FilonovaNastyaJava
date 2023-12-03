import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.InMemoryArticleRepository;
import org.example.services.ArticleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  void updateTest() {
    comments.add(comment);
    articleId = articleService.update(articleId, name, tags, comments);
    Article article = articleService.findById(articleId);
    Assertions.assertEquals(article.getComments(), comments);
  }
  @Test
  void findAllTest() {
    List<Article> articles = articleService.findAll();
    Assertions.assertTrue(articles.size() == 1);
    Assertions.assertEquals(articles.get(0).getName(), name);
    Assertions.assertEquals(articles.get(0).getTags(), tags);
    Assertions.assertEquals(articles.get(0).getComments(), comments);
  }

  @Test
  void deleteTest() {
    articleService.delete(articleId);
    Assertions.assertTrue(inMemoryArticleRepository.getArticlesMapSize() == 0);
  }
}
