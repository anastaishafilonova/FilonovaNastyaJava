package org.example.repositories;

import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.repositories.exceptions.ArticleIdDuplicatedException;
import org.example.repositories.exceptions.ArticleNotFoundException;

import java.util.*;

public interface ArticleRepository {

  ArticleId generateId();

  List<Article> findAll();

  /**
   * @throws ArticleNotFoundException
   */
  Article findById(ArticleId articleId);

  /**
   * @throws ArticleNotFoundException
   */
  void create(Article article);

  /**
   * @throws ArticleIdDuplicatedException
   */
  void update(Article article);

  /**
   * @throws ArticleNotFoundException
   */
  void delete(ArticleId articleId);

}