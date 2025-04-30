package org.comunity.auth;

import org.comunity.auth.domain.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @Test
    void givenPassword_whenMatchSamePassword_thenReturnTrue() {
        // given
        Password password = Password.createEncryptedPassword("password");

        // when
        // then
        assertTrue(password.matchPassword("password"));
    }

    @Test
    void givenPassword_whenMatchDiffPassword_thenReturnFalse() {
        // given
        Password password = Password.createEncryptedPassword("password1");

        // when
        // then
        assertFalse(password.matchPassword("password"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenPasswordIsNull_thenThreowError(String password) {
        assertThrows(IllegalArgumentException.class, () -> Password.createEncryptedPassword(password));
    }
}
