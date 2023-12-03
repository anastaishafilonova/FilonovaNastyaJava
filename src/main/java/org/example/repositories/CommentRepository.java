package org.example.repositories;

import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.exceptions.CommentIdDuplicatedException;
import org.example.repositories.exceptions.CommentNotFoundException;

import java.util.List;

public interface CommentRepository {
  CommentId generateId();

  List<Comment> findAll();

  /**
   * @throws CommentNotFoundException
   */
  Comment findById(CommentId commentId);

  /**
   * @throws CommentNotFoundException
   */
  void create(Comment comment);

  /**
   * @throws CommentIdDuplicatedException
   */
  void update(Comment comment);

  /**
   * @throws CommentNotFoundException
   */
  void delete(CommentId commentId);
}
