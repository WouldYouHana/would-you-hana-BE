package com.hanaro.wouldyouhana.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerGoodRequestDTO {
    private Long questionId;
    private Long customerId;
}
