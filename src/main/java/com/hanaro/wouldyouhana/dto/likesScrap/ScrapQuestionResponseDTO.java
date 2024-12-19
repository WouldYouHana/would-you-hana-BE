package com.hanaro.wouldyouhana.dto.likesScrap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ScrapQuestionResponseDTO {

    private Long requestId;
    private Long questionId;

    // 화면에 보여지는 내용
    private String categoryName;
    private String questionTitle;
    private String customerName;
    private Long likeCount;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String bankerName;
}
