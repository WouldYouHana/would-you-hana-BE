package com.hanaro.wouldyouhana.dto.likesScrap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ScrapPostResponseDTO {

    private Long requestId;
    private Long postId;

    // 화면에 보여지는 내용
    private String categoryName;
    private String title;
    private String customerName;
    private Long likeCount;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
