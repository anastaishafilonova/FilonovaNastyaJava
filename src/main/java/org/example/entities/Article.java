package org.example.entities;

import java.util.*;

public class Article {
  private final ArticleId id;
  private final String name;
  private final Set<String> tags;
  private final List<Comment> comments;

  public Article(ArticleId id, String name, Set<String> tags, List<Comment> comments) {
    this.id = id;
    this.name = name;
    this.tags = tags;
    this.comments = comments;
  }

  public ArticleId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<String> getTags() {
    return tags;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public String getNumberOfComments() {
    return "" + comments.size();
  }

  public Article withName(String newName) {
    return new Article(this.id, newName, this.tags, this.comments);
  }

  public Article withTags(Set<String> tags) {
    return new Article(this.id, this.name, tags, this.comments);
  }

  public Article withComments(List<Comment> comments) {
    return new Article(this.id, this.name, tags, this.comments);
  }

  @Override
  public String toString() {
    return "org.example.entities.Article{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", tags='" + tags.toString() + '\'' +
        ", comments='" + comments.toString() + '\'' +
        '}';
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return id == article.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
