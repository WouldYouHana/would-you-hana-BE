package com.hanaro.wouldyouhana.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostAddRequestDTO {

    @NotNull
    private String title;
    @NotNull
    private Long customerId;
    @NotNull
    private String categoryName;
    @NotNull
    private String communityName;
    @NotNull
    private String content;
}
