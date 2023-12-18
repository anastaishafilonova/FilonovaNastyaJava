import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.InMemoryArticleRepository;
import org.example.repositories.InMemoryCommentRepository;
import org.example.repositories.exceptions.ArticleNotFoundException;
import org.example.repositories.exceptions.CommentNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InMemoryCommentRepositoryTest {
  InMemoryCommentRepository commentRepository = new InMemoryCommentRepository();
  @Test
  void CommentNotFoundException() {
    boolean exceptionThrown = false;
    try {
      Comment comment = commentRepository.findById(new CommentId(10));
    } catch (CommentNotFoundException e) {
      exceptionThrown = true;
    }
    Assertions.assertTrue(exceptionThrown);
  }
}
