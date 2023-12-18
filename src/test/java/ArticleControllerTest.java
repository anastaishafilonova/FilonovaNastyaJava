import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Application;
import org.example.controllers.ArticleController;
import org.example.controllers.responses.ArticleCreateResponse;
import org.example.controllers.responses.ArticleGetResponse;
import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.repositories.ArticleRepository;
import org.example.repositories.InMemoryArticleRepository;
import org.example.services.ArticleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleControllerTest {

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
  void should201IfArticleIsSuccessfullyCreated() throws Exception {
    ArticleService articleService = Mockito.mock(ArticleService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    Application application =
        new Application(List.of(new ArticleController(service, articleService, objectMapper)));
    Mockito.when(articleService.create("first", Set.of("1", "article"), new ArrayList<>())).thenReturn(new ArticleId(1));
    application.start();
    service.awaitInitialization();

    HttpResponse<String> response =
        HttpClient.newHttpClient()
            .send(
                HttpRequest.newBuilder()
                    .POST(
                        BodyPublishers.ofString(
                            """
                                { "name": "article", "tags": ["1", "article"], "comments": [] }"""))
                    .uri(URI.create("http://localhost:%d/api/article".formatted(service.port())))
                    .build(),
                BodyHandlers.ofString(UTF_8));

    assertEquals(201, response.statusCode());
    ArticleCreateResponse articleCreateResponse =
        objectMapper.readValue(response.body(), ArticleCreateResponse.class);
  }

  @Test
  void should201IfArticleIsSuccessfullyFound() throws Exception {
    ArticleService articleService = Mockito.mock(ArticleService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    Application application =
        new Application(List.of(new ArticleController(service, articleService, objectMapper)));

    var articleId = new ArticleId(1);
    var name = "first";
    var tags = Set.of("1", "article");
    var comments = new ArrayList<Comment>();
    Mockito.when(articleService.findById(articleId))
        .thenReturn(new Article(articleId, name, tags, comments));

    application.start();
    service.awaitInitialization();

    HttpResponse<String> response =
        HttpClient.newHttpClient()
            .send(
                HttpRequest.newBuilder()
                    .GET()
                    .uri(
                        URI.create(
                            "http://localhost:%d/article/1".formatted(service.port())))
                    .build(),
                HttpResponse.BodyHandlers.ofString(UTF_8));

    assertEquals(201, response.statusCode());
    ArticleGetResponse articleGetResponse =
        objectMapper.readValue(response.body(), ArticleGetResponse.class);
    assertEquals(name, articleGetResponse.name());
    assertEquals(tags, articleGetResponse.tags());
    assertEquals(comments, articleGetResponse.comments());
  }

  @Test
  void should201IfArticleIsSuccessfullyUpdated() throws IOException, InterruptedException {
    ArticleService articleService = Mockito.mock(ArticleService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    Application application =
        new Application(List.of(new ArticleController(service, articleService, objectMapper)));

    var articleId = new ArticleId(1);
    Mockito.doNothing().when(articleService).update(articleId, "First", Set.of("1", "article"), List.of());

    application.start();
    service.awaitInitialization();

    HttpResponse<String> response =
        HttpClient.newHttpClient()
            .send(
                HttpRequest.newBuilder()
                    .PUT(
                        HttpRequest.BodyPublishers.ofString(
                            """
                                {"articleId": "1", "name": "First", "tags": ["drama", "sci-fi"], "comments": []}"""))
                    .uri(
                        URI.create(
                            "http://localhost:%d/api/article/1".formatted(service.port())))
                    .build(),
                HttpResponse.BodyHandlers.ofString(UTF_8));

    Mockito.verify(articleService, Mockito.times(1))
        .update(articleId, "First", Set.of("drama", "sci-fi"), List.of());
  }
}
