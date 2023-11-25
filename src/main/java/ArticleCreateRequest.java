import java.util.*;

public record ArticleCreateRequest(String name, Set<String> tags, List<Comment> comments) {
}
