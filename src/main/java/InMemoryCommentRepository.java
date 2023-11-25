import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCommentRepository implements CommentRepository{
  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<CommentId, Comment> commentsMap = new ConcurrentHashMap<>();

  @Override
  public CommentId generateId() {
    return new CommentId(nextId.incrementAndGet());
  }

  @Override
  public List<Comment> findAll() {
    return new ArrayList<>(commentsMap.values());
  }

  @Override
  public Comment findById(CommentId commentId) {
    Comment comment = commentsMap.get(commentId);
    if (comment == null) {
      throw new CommentNotFoundException("Cannot find book by id=" + commentId);
    }
    return comment;
  }

  @Override
  public synchronized void create(Comment comment) {
    if (commentsMap.get(comment.getId()) != null) {
      throw new CommentIdDuplicatedException("Book with the given id already exists: " + comment.getId());
    }
    commentsMap.put(comment.getId(), comment);
  }

  @Override
  public synchronized void update(Comment comment) {
    if (commentsMap.get(comment.getId()) == null) {
      throw new CommentNotFoundException("Cannot find book by id=" + comment.getId());
    }
    commentsMap.put(comment.getId(), comment);
  }

  @Override
  public void delete(CommentId commentId) {
    if (commentsMap.remove(commentId) == null) {
      throw new CommentNotFoundException("Cannot find book by id=" + commentId);
    }
  }
}
