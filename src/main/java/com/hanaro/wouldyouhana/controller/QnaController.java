package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.QuestionAddRequest;
import com.hanaro.wouldyouhana.dto.QuestionResponseDTO;
import com.hanaro.wouldyouhana.service.QnaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class QnaController {

    private final QnaService qnaService;

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping("/posts")
    public ResponseEntity<QuestionResponseDTO> addNewQuestion(@Valid @RequestBody QuestionAddRequest questionAddRequest) {
        log.info("executed");
        QuestionResponseDTO createdPost = qnaService.addQuestion(questionAddRequest);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // 댓글 추가


    // 게시글에 대한 댓글 가져오기


    // 댓글 삭제
}
