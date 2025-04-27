package org.comunity.post.repository.entity.post;

import jakarta.persistence.AttributeConverter;
import org.comunity.post.domain.PostPublicationState;

// <변환시켜야 할 enum 값, db에 저장할 타입>
public class PostPublicationStateConverter implements AttributeConverter<PostPublicationState, String> {
    @Override
    public String convertToDatabaseColumn(PostPublicationState postPublicationState) {
        return postPublicationState.name();
    }

    @Override
    public PostPublicationState convertToEntityAttribute(String s) {
        return PostPublicationState.valueOf(s);
    }
}
