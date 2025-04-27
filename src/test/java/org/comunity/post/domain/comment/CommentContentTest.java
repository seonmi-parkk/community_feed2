package org.comunity.post.domain.comment;

import org.comunity.post.domain.content.CommentContent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CommentContentTest {

    @Test
    void givenContentLengthIsOk_whenCreated_thenReturnTextContent() {
        // given
        String text = "this is a test";

        // when
        CommentContent content = new CommentContent(text);

        // then
        assertEquals(text, content.getContentText());
    }

    @Test
    void givenContentLengthIsOver_whenCreated_thenThrowError() {
        // given
        String content = "a".repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @ParameterizedTest
    @ValueSource(strings = {"뷁, 닭, 굵, 삵, 숦"})
    void givenContentLengthIsOverAndKorean_whenCreated_thenThrowError(String koreanWord) {
        // given
        String content = koreanWord.repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentIsEmpty_whenCreated_thenThrowError(String value) {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(value));
    }
}
