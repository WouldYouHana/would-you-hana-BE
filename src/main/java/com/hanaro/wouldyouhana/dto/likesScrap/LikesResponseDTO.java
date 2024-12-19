package com.hanaro.wouldyouhana.dto.likesScrap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LikesResponseDTO {

    private Long id;
    private Long postId;
    private String categoryName;
//    private String nickname;
    private String title;
    private Long likeCount;
    private Long viewCount;
    private LocalDateTime createdAt;
}
