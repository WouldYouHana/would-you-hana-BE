package com.hanaro.wouldyouhana.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class CommentResponseDTO {
//    @NotNull
//    private Long customerId;

    @NotNull
    private String userName;
    @NotNull
    private Long questionId;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime createdAt;
}
