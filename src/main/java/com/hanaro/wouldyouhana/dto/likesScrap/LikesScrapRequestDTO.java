package com.hanaro.wouldyouhana.dto.likesScrap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikesScrapRequestDTO {

    private Long postId;
    private Long customerId;
}
