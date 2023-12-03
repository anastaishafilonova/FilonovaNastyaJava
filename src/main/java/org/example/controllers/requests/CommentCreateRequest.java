package org.example.controllers.requests;

import org.example.entities.ArticleId;

public record CommentCreateRequest(ArticleId articleId, String text) {
}