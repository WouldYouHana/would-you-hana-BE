package com.hanaro.wouldyouhana.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class QuestionAddRequest {

    @NotNull
    private String title;
    @NotNull
    private Long customer_id;
    @NotNull
    private Long category_id;
    @NotNull
    private String location;
    @NotNull
    private String content;
    @NotNull
    private String location;

}
