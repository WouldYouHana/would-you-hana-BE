package com.hanaro.wouldyouhana.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InterestLocationRequestDTO {

    private Long customerId;
    private String location;
}
