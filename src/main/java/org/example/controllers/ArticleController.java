package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controllers.requests.ArticleCreateRequest;
import org.example.controllers.requests.ArticleGetRequest;
import org.example.controllers.requests.ArticlePutRequest;
import org.example.controllers.responses.*;
import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.repositories.exceptions.ArticleNotFoundException;
import org.example.services.ArticleService;
import org.example.services.exceptions.ArticleCreateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.List;

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
    getAllArticles();
    getArticle();
    putArticle();
  }

  private void createArticle() {
    service.post(
        "/api/article",
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
            LOG.warn("Cannot create article", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void getAllArticles() {
    service.get(
        "/articles",
        (Request request, Response response) -> {
          response.type("/application/json");
          try {
            List<Article> articles = articleService.findAll();
            response.status(201);
            return objectMapper.writeValueAsString(new ArticleGetAllArticlesResponse(articles));
          } catch (ArticleNotFoundException e) {
            LOG.warn("Cannot found articles", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void getArticle() {
    service.get(
        "/article/:articleId",
        (Request request, Response response) -> {
          response.type("/application/json");
          String body = request.body();
          ArticleId articleId = new ArticleId(Long.parseLong(request.params("articleId")));
          try {
            Article articleWithId = articleService.findById(articleId);
            response.status(201);
            return objectMapper.writeValueAsString(new ArticleGetResponse(articleWithId.getName(), articleWithId.getTags(),
                articleWithId.getComments()));
          } catch (ArticleNotFoundException e) {
            LOG.warn("Cannot found article with id " + articleId, e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void putArticle() {
    service.put(
        "/api/article/:articleId",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          ArticlePutRequest articlePutRequest = objectMapper.readValue(body,
              ArticlePutRequest.class);
          try {
            articleService.update(articlePutRequest.articleId(), articlePutRequest.name(), articlePutRequest.tags(), articlePutRequest.comments());
            response.status(201);
            return objectMapper.writeValueAsString(new ArticlePutResponse());
          } catch (ArticleNotFoundException e) {
            LOG.warn("Cannot update article with id " + articlePutRequest.articleId(), e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }
}