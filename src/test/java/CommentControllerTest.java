import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Application;
import org.example.controllers.ArticleController;
import org.example.controllers.CommentController;
import org.example.controllers.responses.ArticleCreateResponse;
import org.example.controllers.responses.ArticleGetResponse;
import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.services.ArticleService;
import org.example.services.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentControllerTest {
  private Service service;

  @BeforeEach
  void beforeEach() {
    service = Service.ignite();
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void should201IfCommentIsSuccessfullyCreated() throws Exception {
    CommentService commentService = Mockito.mock(CommentService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    Application application =
        new Application(List.of(new CommentController(service, commentService, objectMapper)));
    Mockito.when(commentService.create(new ArticleId(1), "Hello!")).thenReturn(new CommentId(1));
    application.start();
    service.awaitInitialization();

    HttpResponse<String> response =
        HttpClient.newHttpClient()
            .send(
                HttpRequest.newBuilder()
                    .POST(
                        HttpRequest.BodyPublishers.ofString(
                            """
                                { "articleId": "1", "text": "Hello!" }"""))
                    .uri(URI.create("http://localhost:%d/api/comments".formatted(service.port())))
                    .build(),
                HttpResponse.BodyHandlers.ofString(UTF_8));

    assertEquals(201, response.statusCode());
  }
}
