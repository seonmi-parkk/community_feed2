package org.comunity.post.application.dto;

import org.comunity.post.domain.PostPublicationState;

public record UpdatePostRequestDto (Long userId, String content, PostPublicationState state) {

}
