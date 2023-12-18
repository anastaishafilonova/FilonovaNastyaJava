package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controllers.requests.CommentCreateRequest;
import org.example.controllers.requests.CommentDeleteRequest;
import org.example.controllers.responses.CommentCreateResponse;
import org.example.controllers.responses.CommentDeleteResponse;
import org.example.controllers.responses.ErrorResponse;
import org.example.entities.CommentId;
import org.example.services.ArticleService;
import org.example.services.CommentService;
import org.example.services.exceptions.CommentCreateException;
import org.example.services.exceptions.CommentDeleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;
import spark.Spark;

public class CommentController implements Controller {
  private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

  private final Service service;
  private final CommentService commentService;

  private final ObjectMapper objectMapper;

  public CommentController(Service service, CommentService commentService, ObjectMapper objectMapper) {
    this.service = service;
    this.commentService = commentService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    createComment();
    deleteComment();
  }

  private void createComment() {
    service.post(
        "/api/comments",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          CommentCreateRequest commentCreateRequest = objectMapper.readValue(body,
              CommentCreateRequest.class);
          try {
            CommentId commentId = commentService.create(commentCreateRequest.articleId(), commentCreateRequest.text());
            response.status(201);
            return objectMapper.writeValueAsString(new CommentCreateResponse(commentId));
          } catch (CommentCreateException e) {
            LOG.warn("Cannot create comment", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void deleteComment() {
    service.delete("/api/comments/:commentId",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          CommentDeleteRequest commentDeleteRequest = objectMapper.readValue(body, CommentDeleteRequest.class);
          try {
            commentService.delete(commentDeleteRequest.commentId());
            response.status(201);
            return objectMapper.writeValueAsString(new CommentDeleteResponse(commentDeleteRequest.commentId()));
          } catch (CommentDeleteException e) {
            LOG.warn("Cannot delete comment", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
          });
        }
  }
