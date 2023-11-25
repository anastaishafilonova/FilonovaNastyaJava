import java.util.List;

public interface CommentRepository {
  CommentId generateId();

  List<Comment> findAll();

  /**
   * @throws CommentNotFoundException
   */
  Comment findById(CommentId commentId);

  /**
   * @throws CommentIdDuplicatedException
   */
  void create(Comment comment);

  /**
   * @throws CommentNotFoundException
   */
  void update(Comment comment);

  /**
   * @throws CommentNotFoundException
   */
  void delete(CommentId commentId);
}
