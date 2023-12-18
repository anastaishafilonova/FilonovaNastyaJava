package org.example.repositories;

import org.example.entities.Article;
import org.example.entities.Comment;
import org.example.entities.CommentId;
import org.example.repositories.CommentRepository;
import org.example.repositories.exceptions.CommentIdDuplicatedException;
import org.example.repositories.exceptions.CommentNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCommentRepository implements CommentRepository {
  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<CommentId, Comment> commentsMap = new ConcurrentHashMap<>();

  public int getCommentsMapSize() {
    return commentsMap.size();
  }

  @Override
  public CommentId generateId() {
    return new CommentId(nextId.incrementAndGet());
  }


  @Override
  public Comment findById(CommentId commentId) {
    Comment comment = commentsMap.get(commentId);
    if (comment == null) {
      throw new CommentNotFoundException("Cannot find comment by id=" + commentId);
    }
    return comment;
  }

  @Override
  public synchronized void create(Comment comment) {
    if (commentsMap.get(comment.getId()) != null) {
      throw new CommentIdDuplicatedException("Comment with the given id already exists: " + comment.getId());
    }
    commentsMap.put(comment.getId(), comment);
  }

  @Override
  public void delete(CommentId commentId) {
    if (commentsMap.remove(commentId) == null) {
      throw new CommentNotFoundException("Cannot find comment by id=" + commentId);
    }
  }
}
