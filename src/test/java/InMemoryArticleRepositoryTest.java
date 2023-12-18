import org.example.entities.ArticleId;
import org.example.repositories.ArticleRepository;
import org.example.repositories.InMemoryArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InMemoryArticleRepositoryTest {
  @Test
  public void generateIdTest() {
    ArticleRepository articleRepository = new InMemoryArticleRepository();
    ArticleId id1 = articleRepository.generateId();
    ArticleId id2 = articleRepository.generateId();
    ArticleId id3 = articleRepository.generateId();
    Assertions.assertEquals(new ArticleId(1), id1);
    Assertions.assertEquals(new ArticleId(2), id2);
    Assertions.assertEquals(new ArticleId(3), id3);
  }
}
