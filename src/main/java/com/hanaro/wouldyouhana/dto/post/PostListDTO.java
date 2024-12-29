package com.hanaro.wouldyouhana.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostListDTO {
    private Long postId;
    private String nickname;
    private String categoryName;
    private String title;
    private String content;
    private String location;
    private LocalDateTime createdAt;
    private int commentCount;
    private Long likeCount;
    private Long scrapCount;
    private Long viewCount;
    private List<String> filePaths;
}
