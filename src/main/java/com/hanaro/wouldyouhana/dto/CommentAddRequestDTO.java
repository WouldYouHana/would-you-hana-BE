package com.hanaro.wouldyouhana.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor

public class CommentAddRequestDTO {

    @NotNull
    private Long customerId;
    @NotNull
    private String content;

}
