package org.comunity.admin.ui.dto.posts;

import lombok.*;
import org.comunity.common.utils.TimeCalculator;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostTableResponseDto {
    @Getter
    private Long postId;
    @Getter
    private Long userId;
    @Getter
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getContent() {
        // 내용이 길어지면 10글자만 보여주고 ...으로 표시
        if (content.length() > 10) {
            return content.substring(0, 10) + "...";
        }
        return content;
    }

    public String getCreatedAt() {
        return TimeCalculator.getFormattedDate(createdAt);
    }

    public String getUpdatedAt() {
        return TimeCalculator.getFormattedDate(updatedAt);
    }
}
