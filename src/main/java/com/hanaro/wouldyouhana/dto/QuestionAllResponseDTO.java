package com.hanaro.wouldyouhana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAllResponseDTO {

    private Long question_id;
    private Long customer_id;
    private Long category_id;
    private String title;
    private String content;
    private String location;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer likeCount;
    private Integer scrapCount;
    private Integer viewCount;
    private List<String> file;

}
