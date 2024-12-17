package com.hanaro.wouldyouhana.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAllResponseDTO {

    private Long questionId;
    private Long customerId;
    private String categoryName;
    private String title;
    private String content;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long scrapCount;
    private Long viewCount;
    private List<String> filePaths;

}
