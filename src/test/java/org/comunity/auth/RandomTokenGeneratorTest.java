package org.comunity.auth;

import org.comunity.auth.domain.RandomTokenGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomTokenGeneratorTest {

    @Test
    void whenGenerateToken_thenReturnTokenWithCorrectLength() {
        // when
        String token = RandomTokenGenerator.generateToken();

        // then
        assertNotNull(token);
        assertEquals(16, token.length());
    }

    @Test
    void whenGenerateToken_thenReturnTokenWithValidCharacters() {
        // when
        String token = RandomTokenGenerator.generateToken();

        // then
        assertNotNull(token);
        assertTrue(token.matches("[0-9A-Za-z]{16}"));
    }

    @Test
    void whenGenerateTokenMultipleTimes_thenReturnUniqueTokens() {
        // when
        String token1 = RandomTokenGenerator.generateToken();
        String token2 = RandomTokenGenerator.generateToken();

        // then
        assertNotNull(token1);
        assertNotNull(token2);
        assertNotEquals(token1, token2);
    }

}
