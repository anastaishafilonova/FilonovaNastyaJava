import org.example.entities.*;
import org.example.entities.CommentId;
import org.example.repositories.InMemoryArticleRepository;
import org.example.repositories.InMemoryCommentRepository;
import org.example.services.ArticleService;
import org.example.services.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommentServiceTest {
  InMemoryCommentRepository inMemoryCommentRepository = new InMemoryCommentRepository();
  ArticleService articleService = new ArticleService(new InMemoryArticleRepository());
  CommentService commentService = new CommentService(inMemoryCommentRepository, articleService);
  String text = "Hello!";
  String name = "first";
  Set<String> tags = Set.of("1", "article");
  List<Comment> comments = new ArrayList<>();
  ArticleId articleId = articleService.create(name, tags, comments);
  CommentId commentId = commentService.create(articleId, text);

  @Test
  void createTest() {
    Assertions.assertEquals(commentId, new CommentId(1));
    Assertions.assertTrue(inMemoryCommentRepository.getCommentsMapSize() == 1);
  }

  @Test
  void deleteTest() {
    commentService.delete(commentId);
    Assertions.assertTrue(inMemoryCommentRepository.getCommentsMapSize() == 0);
  }
}
