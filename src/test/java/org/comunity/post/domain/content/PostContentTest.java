package org.comunity.post.domain.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PostContentTest {

    @Test
    void givenContentLengthIsOk_whenCreated_thenReturnTextContent() {
        // given
        String text ="this is a test";

        // when
        PostContent content = new PostContent(text);

        // then
        assertEquals(text, content.contentText);
    }

    @Test
    void givenContentLengthIsOver_whenCreated_thenThrowError() {
        // given
        String content = "a".repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    // 뷁 외에도 여러 글자 테스트하고 싶은경우 그때마다 content 변수 여러개 만들면 가독성이 떨어짐
    // -> ParameterizedTest라는 junit 어노테이션 활용
    @ParameterizedTest
    @ValueSource(strings = {"뷁, 닭, 굵, 삵, 숦"})
    void givenContentLengthIsOverAndKorean_whenCreated_thenThrowError(String koreanWord){
        // given
        String content = koreanWord.repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @Test
    void givenContentLengthIsUnder_whenCreated_thenThrowError(){
        // given
        String content = "a".repeat(4);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentIsEmpty_whenCreated_thenThrowError(String value){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(value));
    }

    // PostContent의 생성자와 update는 똑같은 로직 -> 두번씩 test를 작성해줘야해서 번거로움.
    // github copilot -> 자동완성기능 만들어줌
        // 주의점
            // 테스트코드 베이스를 짜고 사용하기
            // 테스트할 클래스 파일창(여기선 PostContent와 Content class)들을 옆에 띄우고 만들어줘야 코파일럿이 인식 가능
    @Test
    void givenContentLengthIsOk_whenUpdated_thenNotThrowError() {
        // given
        String text = "this is a test";
        PostContent content = new PostContent(text);

        // when
        content.updateContent("this is a test2");

        // then
        assertEquals("this is a test2", content.contentText);
    }

    @Test
    void givenContentLengthIsOk_whenUpdated_thenReturnUpdatedContent() {
        // given
        String text = "this is a test";
        PostContent content = new PostContent(text);

        // when
        content.updateContent("this is a updated content");

        // then
        assertEquals("this is a updated content", content.contentText);
    }

    @Test
    void givenContentLengthIsOver_whenUpdated_thenThrowError() {
        // given
        String text = "this is a test content";
        PostContent postContent = new PostContent(text);

        // when, then

        String value = "a".repeat(501);
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"뷁, 닭, 굵, 삵, 숦"})
    void givenContentLengthIsOverAndKorean_whenUpdated_thenThrowError(String koreanWord){
        // given
        String text = "this is a test content";
        PostContent postContent = new PostContent(text);

        // when, then
        String value = koreanWord.repeat(501);
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(value));
    }

    @Test
    void givenContentLengthIsUnder_whenUpdated_thenThrowError(){
        // given
        String text = "this is a test content";
        PostContent postContent = new PostContent(text);

        // when, then
        String value = "a".repeat(4);
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(value));
    }
}
