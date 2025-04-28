package org.comunity.acceptance.steps;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.dto.FollowUserRequestDto;
import org.springframework.http.MediaType;

// http 요청을 RestAssured를 통해서 보내고 응답을 받아서 리턴
public class UserAcceptanceSteps {
    public static ExtractableResponse<Response> createUser(CreateUserRequestDto dto) {
        return RestAssured
                .given()
                .body(dto) // 바디가 dto로 주어졌을때
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/user") // post 요청을 보냄
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> followUser(FollowUserRequestDto dto) {
        return RestAssured
                .given()
                .body(dto) // 바디가 dto로 주어졌을때
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/relation/follow") // post 요청을 보냄
                .then()
                .extract();
    }
}
