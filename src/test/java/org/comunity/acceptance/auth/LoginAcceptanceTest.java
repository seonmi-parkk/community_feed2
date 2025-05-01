package org.comunity.acceptance.auth;

import org.comunity.acceptance.utils.AcceptanceTestTemplate;
import org.comunity.auth.application.dto.LoginRequestDto;
import org.comunity.auth.domain.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.comunity.acceptance.steps.LoginAcceptanceSteps.*;
import static org.comunity.acceptance.steps.SignUpAcceptanceSteps.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginAcceptanceTest extends AcceptanceTestTemplate {
    private final String email = "email@email.com";
    private final TokenProvider tokenProvider = new TokenProvider("testteststestteststestteststestteststestteststestteststestteststestteststestteststesttests");


    /*
     * 1. 이메일을 보내고
     * 2. 이메일을 확인하고
     * 3. 사용자를 등록한다.
     * */
    @BeforeEach
    void setUp() {
        super.cleanUp();
        this.createUser(email);
    }

    @Test
    void givenEmailAndPassword_whenLogin_thenToken() {
        // given
        LoginRequestDto dto = new LoginRequestDto(email, "password");

        // when
        String token = requestLoginGetToken(dto);

        // then
        assertNotNull(token);
        Long id = tokenProvider.getUserId(token);
        assertEquals(10L, id);
    }

    @Test
    void givenWrongPassword_whenLogin_thenException() {
        // given
        LoginRequestDto dto = new LoginRequestDto(email, "wrongPassword");

        // when
        Integer code = requestLoginGetCode(dto);

        // then
        assertEquals(500, code);
    }

}
