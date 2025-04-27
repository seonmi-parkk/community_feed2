package org.comunity.user.application.dto;

public record FollowUserRequestDto(Long userId, Long targetUserId) {
}
