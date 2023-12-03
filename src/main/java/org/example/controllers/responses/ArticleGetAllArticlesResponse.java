package org.example.controllers.responses;

import org.example.entities.Article;

import java.util.List;

public record ArticleGetAllArticlesResponse(List<Article> articles) {
}
