package com.hanaro.wouldyouhana.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostAllResponseDTO {
    private Long postId;
    private Long customerId;
    private String categoryName;
    private String title;
    private String content;
    private String communityName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long scrapCount;
    private Long viewCount;
    private List<String> filePaths;
}
