package com.hanaro.wouldyouhana.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodayQnaListDTO {

    private Long questionId;
    private String title;
    private Long viewCount;

}
