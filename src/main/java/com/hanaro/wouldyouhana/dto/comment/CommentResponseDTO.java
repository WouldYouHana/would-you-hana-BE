package com.hanaro.wouldyouhana.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDTO {

    @NotNull
    private String nickname;

    @NotNull
    private Long id;
    // qna와 커뮤니티 포스트 공통으로 사용

    @NotNull
    private String content;
    @NotNull
    private LocalDateTime createdAt;

}
