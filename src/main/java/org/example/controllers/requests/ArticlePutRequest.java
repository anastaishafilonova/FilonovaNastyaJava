package org.example.controllers.requests;

import org.example.entities.ArticleId;
import org.example.entities.Comment;

import java.util.List;
import java.util.Set;

public record ArticlePutRequest(ArticleId articleId, String name, Set<String> tags, List<Comment> comments) {
}
