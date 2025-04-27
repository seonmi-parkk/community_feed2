package org.comunity.post.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
// 상속관계에서 부모 필드와 자식 필드 모두 빌더로 세팅할 수 있게 해줌.
// 부모 클래스, 자식 클래스 모두에 붙여야함.
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostContentResponseDto extends GetContentResponseDto {
    private Integer commentCount;
}
