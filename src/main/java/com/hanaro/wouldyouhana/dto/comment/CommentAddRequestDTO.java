package com.hanaro.wouldyouhana.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CommentAddRequestDTO {

    @NotNull
    private Long customerId;
    @NotNull
    private String content;

}
