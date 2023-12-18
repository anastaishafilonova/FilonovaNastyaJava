package org.example.controllers.requests;

import org.example.entities.Comment;

import java.util.*;

public record ArticleCreateRequest(String name, Set<String> tags, List<Comment> comments) {
}
