package org.comunity.post.domain.content;

// 글 작성시, 댓글 작성시 유효성(글자수) 체크하는 로직이 비슷함.
// 글자수만 다르고, 미니멈이 없다는 점만 조금 다름.
// => 검증이라는 행위를 추상화 시키고 다형성을 적용.(abstrans class 혹은 interface만듦)

//Content 클래스를 이용해서 확장한것은 solid 원칙 중 어떤 부분이 잘 지켜졌나?
// srp : single response principle => 하나의 기능이 변경될때 하나의 객체만 변경이 되기 때문에
// open-closed 원칙 : 수정에는 닫혀있고 확장에는 열려있다. => 기능이 추가되어도 기존코드가 변경되지 않기 때문에

import org.comunity.post.domain.common.DatetimeInfo;

public abstract class Content {
    protected String contentText;
    protected final DatetimeInfo dateTimeInfo;

    protected Content(String contentText) {
        checkText(contentText);
        this.contentText = contentText;
        this.dateTimeInfo = new DatetimeInfo();
        this.dateTimeInfo.updateEditDatetime();
    }

    public void updateContent(String updateContent) {
        checkText(updateContent);
        this.contentText = updateContent;
        this.dateTimeInfo.updateEditDatetime();
    }

    protected abstract void checkText(String contentText);

    public String getContentText() {
        return contentText;
    }

}
