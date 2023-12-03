package org.example.services;

import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;
import org.example.repositories.ArticleRepository;
import org.example.repositories.exceptions.ArticleIdDuplicatedException;
import org.example.repositories.exceptions.ArticleNotFoundException;
import org.example.services.exceptions.ArticleCreateException;
import org.example.services.exceptions.ArticleDeleteException;
import org.example.services.exceptions.ArticleFindException;
import org.example.services.exceptions.ArticleUpdateException;

import java.util.*;

public class ArticleService {
  private final ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public List<Article> findAll() {
    return articleRepository.findAll();
  }

  public Article findById(ArticleId articleId) {
    try {
      return articleRepository.findById(articleId);
    } catch (ArticleNotFoundException e) {
      throw new ArticleFindException("Cannot find article by id=" + articleId, e);
    }
  }

  public ArticleId create(String name, Set<String> tags, List<Comment> comments) {
    ArticleId articleId = articleRepository.generateId();
    Article article = new Article(articleId, name, tags, comments);
    try {
      articleRepository.create(article);
    } catch (ArticleIdDuplicatedException e) {
      throw new ArticleCreateException("Cannot create article", e);
    }
    return articleId;
  }

  public ArticleId update(ArticleId articleId, String name, Set<String> tags, List<Comment> comments) {
    Article article;
    try {
      article = articleRepository.findById(articleId);
    } catch (ArticleNotFoundException e) {
      throw new ArticleUpdateException("Cannot find article with id=" + articleId, e);
    }

    try {
      articleRepository.update(
          article.withName(name)
              .withTags(tags)
                  .withComments(comments)
      );
    } catch (ArticleNotFoundException e) {
      throw new ArticleUpdateException("Cannot update article with id=" + articleId, e);
    }
    return articleId;
  }

  public void delete(ArticleId articleId) {
    try {
      articleRepository.delete(articleId);
    } catch (ArticleNotFoundException e) {
      throw new ArticleDeleteException("Cannot delete article with id=" + articleId, e);
    }
  }
}
