package com.hanaro.wouldyouhana.dto.answer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AnswerResponseDTO {

    @NotNull
    private Long bankerId;
    @NotNull
    private Long questionId;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
