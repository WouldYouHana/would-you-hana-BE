package com.hanaro.wouldyouhana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikesScrapRequestDTO {

    private boolean selected;
    private Long questionId;
    private Long customerId;
}
