import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Application;
import org.example.controllers.ArticleController;
import org.example.controllers.ArticleFreemarkerController;
import org.example.controllers.CommentController;
import org.example.entities.ArticleId;
import org.example.entities.CommentId;
import org.example.repositories.InMemoryArticleRepository;
import org.example.repositories.InMemoryCommentRepository;
import org.example.services.ArticleService;
import org.example.services.CommentService;
import org.example.templates.TemplateFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
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
  void endToEndTest() throws Exception {
    InMemoryArticleRepository inMemoryArticleRepository = Mockito.mock(InMemoryArticleRepository.class);
    InMemoryCommentRepository inMemoryCommentRepository = Mockito.mock(InMemoryCommentRepository.class);
    ArticleService articleService = Mockito.mock(ArticleService.class);
    CommentService commentService = Mockito.mock(CommentService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new ArticleController(
                service,
                articleService,
                objectMapper
            ),
            new CommentController(
                service,
                commentService,
                objectMapper
            ),
            new ArticleFreemarkerController(
                service,
                articleService,
                TemplateFactory.freeMarkerEngine()
            )
        )
    );
    Mockito.when(articleService.create("article", Set.of("1", "article"), new ArrayList<>(List.of())))
        .thenReturn(new ArticleId(1));
    Mockito.when(commentService.create(new ArticleId(1), "Hello!"))
        .thenReturn(new CommentId(1));
    application.start();
    service.awaitInitialization();
    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "name": "article", "tags": ["1", "article"], "comments": [] }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/article".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    HttpResponse<String> response1 = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "articleId": "1", "text": "Hello!"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/comments".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    HttpResponse<String> response2 = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            {"commentId": "1"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/comments/:commentId".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    HttpResponse<String> response3 = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .PUT(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            {"articleId": "1", "name": "First", "tags": ["drama", "sci-fi"]}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/article/:articleId".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    HttpResponse<String> response4 = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            {"articleId": "1"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/article/:articleId".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, response.statusCode());
    assertEquals(201, response1.statusCode());
    assertEquals(404, response2.statusCode());
    assertEquals(201, response3.statusCode());
    assertEquals(404, response4.statusCode());
  }
}
