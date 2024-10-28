package com.hanaro.wouldyouhana.dto.answer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerAddRequestDTO {

    @NotNull
    private Long bankerId;
    @NotNull
    private String content;
}
