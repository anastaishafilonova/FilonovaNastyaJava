package org.example.controllers.requests;

import org.example.entities.CommentId;

public record CommentDeleteRequest(CommentId commentId) {
}
