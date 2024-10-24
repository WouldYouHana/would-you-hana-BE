package com.hanaro.wouldyouhana.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class CommentResponseDTO {
    @NotNull
    private Long customer_id;
    @NotNull
    private Long question_id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime created_at;
}
