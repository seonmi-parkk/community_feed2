package org.comunity.user.application.dto;

import org.comunity.user.domain.User;

public record GetUserResponseDto (Long id, String name, String profileImage, Integer followingCount, Integer followerCount){
    // record는 this()로만 추가 생성자를 만들 수 있다.
        // record의 모든 필드는 final이고, 자동 생성되는 생성자에서만 값을 세팅할 수 있음.
    public GetUserResponseDto(User user){
        this(user.getId(), user.getName(), user.getProfileImage(), user.followingCount(), user.followerCount());
    }

}
