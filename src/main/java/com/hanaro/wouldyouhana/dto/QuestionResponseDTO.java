package com.hanaro.wouldyouhana.dto;

import com.hanaro.wouldyouhana.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long question_id;
    private Long customer_id;
    private Long category_id;
    private String title;
    private String content;
    private String location;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Long like_count;
    private Long scrap_count;
    private Long view_count;
    private List<CommentDTO> commentList;

}
