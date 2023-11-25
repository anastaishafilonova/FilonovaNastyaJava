import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

public class ArticleController implements Controller {
  private static final Logger LOG = (Logger) LoggerFactory.getLogger(ArticleController.class);

  private final Service service;
  private final ArticleService articleService;
  private final ObjectMapper objectMapper;

  public ArticleController(Service service, ArticleService articleService, ObjectMapper objectMapper) {
    this.service = service;
    this.articleService = articleService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    createArticle();
  }

  private void createArticle() {
    service.post(
        "/api/articles",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          ArticleCreateRequest articleCreateRequest = objectMapper.readValue(body,
              ArticleCreateRequest.class);
          try {
            ArticleId articleId = articleService.create(articleCreateRequest.name(), articleCreateRequest.tags(), articleCreateRequest.comments());
            response.status(201);
            return objectMapper.writeValueAsString(new ArticleCreateResponse(articleId));
          } catch (ArticleCreateException e) {
            LOG.warn("Cannot create book", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }
}