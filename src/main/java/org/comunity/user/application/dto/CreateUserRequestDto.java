package org.comunity.user.application.dto;

// dto를 만들때  java 14 부터 지원되는 자바 레코드
// 다음과 같이 final 불변 변수로만 구성되어있고 getter만 가진 객체들은 레코드 형태로 변경 가능
// 레코드 객체는 toString, equal, hashcode도 자동으로 생성해줌
//public class CreateUserRequestDto {
//    private final String name;
//    private final String profileImageUrl;
//
//    public CreateUserRequestDto(String name, String profileImageUrl) {
//        this.name = name;
//        this.profileImageUrl = profileImageUrl;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getProfileImageUrl() {
//        return profileImageUrl;
//    }
//}

public record CreateUserRequestDto(String name, String profileImageUrl) {
}


