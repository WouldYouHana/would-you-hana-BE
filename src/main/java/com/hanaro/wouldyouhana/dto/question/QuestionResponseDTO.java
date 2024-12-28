package com.hanaro.wouldyouhana.dto.question;

import com.hanaro.wouldyouhana.domain.Answer;
import com.hanaro.wouldyouhana.dto.answer.AnswerResponseDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long questionId;
    private Long customerId;
    private String nickname;
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
    private AnswerResponseDTO answer;
    private List<CommentDTO> commentList;

}
