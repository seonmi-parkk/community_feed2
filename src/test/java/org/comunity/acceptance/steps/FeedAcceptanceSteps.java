package org.comunity.acceptance.steps;

import io.restassured.RestAssured;
import org.comunity.post.application.dto.CreatePostRequestDto;
import org.comunity.post.ui.dto.GetPostContentResponseDto;
import org.springframework.http.MediaType;

import java.util.List;

public class FeedAcceptanceSteps {

    // 글 생성 후 생성된 글의 id를 리턴
    public static Long requestCreatePost(CreatePostRequestDto dto){
        return RestAssured
                .given().log().all() // 요청을 보낼때 로그를 남김
                .body(dto) // 바디가 dto로 주어졌을때
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/post")
                .then().log().all() // 응답을 받을때 로그를 남김
                .extract()
                .jsonPath()
                .getObject("value", Long.class);
    }

    // 피드의 정보를 가지고 오기
    public static List<GetPostContentResponseDto> requestFeed(String token){
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .getList("value", GetPostContentResponseDto.class);
    }

    public static Integer requestFeedCode(String token){
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .get("code");
    }


}
