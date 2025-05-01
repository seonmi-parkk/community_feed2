package org.comunity.acceptance.steps;

import io.restassured.RestAssured;
import org.comunity.auth.application.dto.LoginRequestDto;
import org.comunity.auth.application.dto.UserAccessTokenResponseDto;
import org.springframework.http.MediaType;

public class LoginAcceptanceSteps {

    public static String requestLoginGetToken(LoginRequestDto dto) {
        UserAccessTokenResponseDto res = RestAssured.given()
                .body(dto)
                .contentType("application/json")
                .when()
                .post("/login")
                .then()
                .extract()
                .jsonPath()
                .getObject("value", UserAccessTokenResponseDto.class);
        return res.accessToken();
    }

    public static Integer requestLoginGetCode(LoginRequestDto dto) {
        return RestAssured.given()
                .body(dto)
                .contentType("application/json")
                .when()
                .post("/login")
                .then()
                .extract()
                .jsonPath()
                .getObject("code", Integer.class);
    }

}
