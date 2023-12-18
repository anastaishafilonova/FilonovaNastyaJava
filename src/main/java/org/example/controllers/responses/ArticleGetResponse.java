package org.example.controllers.responses;

import org.example.entities.Article;
import org.example.entities.ArticleId;
import org.example.entities.Comment;

import java.util.List;
import java.util.Set;

public record ArticleGetResponse(String name, Set<String> tags, List<Comment> comments) {
}
