package org.comunity.post.application.dto;

import org.comunity.post.domain.PostPublicationState;

public record CreatePostRequestDto(Long userId, String content, PostPublicationState state) {
}
