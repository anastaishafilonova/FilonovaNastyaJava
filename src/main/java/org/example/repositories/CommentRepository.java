package org.example.repositories;

import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.exceptions.CommentIdDuplicatedException;
import org.example.repositories.exceptions.CommentNotFoundException;

import java.util.List;

public interface CommentRepository {
  CommentId generateId();

  Comment findById(CommentId commentId);

  /**
   * @throws CommentNotFoundException
   */
  void create(Comment comment);

  /**
   * @throws CommentIdDuplicatedException
   */

  void delete(CommentId commentId);
}
