package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import org.example.controllers.ArticleController;
import org.example.controllers.ArticleFreemarkerController;
import org.example.controllers.CommentController;
import org.example.repositories.InMemoryArticleRepository;
import org.example.repositories.InMemoryCommentRepository;
import org.example.services.ArticleService;
import org.example.services.CommentService;
import org.example.templates.TemplateFactory;
import spark.Service;

public class Main {

  public static void main(String[] args) {
    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    final var articleService = new ArticleService(new InMemoryArticleRepository());
    final var commentService = new CommentService(
      new InMemoryCommentRepository(),
        articleService
    );
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
    application.start();
  }
}
