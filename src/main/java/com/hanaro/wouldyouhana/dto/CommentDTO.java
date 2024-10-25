package com.hanaro.wouldyouhana.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private Long parentComment_id;
    private String content;
    private Long customer_id;
    private LocalDateTime created_at;

}
