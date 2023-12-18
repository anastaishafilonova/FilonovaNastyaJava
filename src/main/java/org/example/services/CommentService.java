package org.example.services;

import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.CommentRepository;
import org.example.repositories.exceptions.CommentIdDuplicatedException;
import org.example.repositories.exceptions.CommentNotFoundException;
import org.example.services.exceptions.CommentCreateException;
import org.example.services.exceptions.CommentDeleteException;

import java.util.*;

public class CommentService {
  private final CommentRepository commentRepository;
  private final ArticleService articleService;

  public CommentService(CommentRepository commentRepository, ArticleService articleService) {
    this.commentRepository = commentRepository;
    this.articleService = articleService;
  }

  public CommentId create(ArticleId articleId, String text) {
    CommentId commentId = commentRepository.generateId();
    Comment comment = new Comment(commentId, articleId, text);
    try {
      commentRepository.create(comment);
      Article articleWithComment = articleService.findById(comment.getArticleId());
      List<Comment> newListOfComments = articleWithComment.getComments();
      newListOfComments.add(comment);
      articleService.update(articleWithComment.getId(), articleWithComment.getName(), articleWithComment.getTags(), newListOfComments);
    } catch (CommentIdDuplicatedException e) {
      throw new CommentCreateException("Cannot create comment", e);
    }
    return commentId;
  }

  public void delete(CommentId commentId) {
    try {
      Comment comment = commentRepository.findById(commentId);
      commentRepository.delete(commentId);
      Article articleWithComment = articleService.findById(comment.getArticleId());
      List<Comment> newListOfComments = articleWithComment.getComments();
      newListOfComments.remove(comment);
      articleService.update(articleWithComment.getId(), articleWithComment.getName(), articleWithComment.getTags(), newListOfComments);
    } catch (CommentNotFoundException e) {
      throw new CommentDeleteException("Cannot delete comment with id=" + commentId, e);
    }
  }
}
