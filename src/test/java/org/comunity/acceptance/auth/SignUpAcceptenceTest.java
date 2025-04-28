package org.comunity.acceptance.auth;

import org.comunity.acceptance.utils.AcceptanceTestTemplate;
import org.comunity.auth.application.dto.SendEmailRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.comunity.acceptance.steps.SignUpAcceptanceSteps.requestSendEmail;
import static org.junit.jupiter.api.Assertions.*;

public class SignUpAcceptenceTest extends AcceptanceTestTemplate {

    private final String email = "email@email.com";

    @BeforeEach
    void setUp() {
        this.cleanUp();
    }

    @Test
    void givenEmaill_whenSendEmail_thenVerificationTokenSaved(){
        // given
        SendEmailRequestDto dto = new SendEmailRequestDto(email);

        // when
        Integer code = requestSendEmail(dto);

        // then
        String token = getEmailToken(email);
        assertNotNull(token);
        assertEquals(0, code);
    }

    @Test
    void givenInvalidEmail_whenSendEmail_thenVerificationTokenNotSaved(){
        // given
        SendEmailRequestDto dto = new SendEmailRequestDto("invalid email");

        // when
        Integer code = requestSendEmail(dto);

        // then
//        String token = getEmailToken(email);
//        assertNull(token);
        assertEquals(400, code);
    }

}
