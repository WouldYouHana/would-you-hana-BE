package com.hanaro.wouldyouhana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LikesScrapResponseDTO {

    private Long scrapId;
    private Long questionId;

    // 화면에 보여지는 내용
    private String categoryName;
    private String questionTitle;
    private Integer likeCount;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private String bankerName;
}
