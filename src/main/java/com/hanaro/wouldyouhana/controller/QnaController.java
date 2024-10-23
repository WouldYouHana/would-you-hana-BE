package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.QuestionAddRequest;
import com.hanaro.wouldyouhana.service.QnaService;
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
    public ResponseEntity<Question> addNewQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        log.info("executed");
        return new ResponseEntity<>(qnaService.addQuestion(questionAddRequest), HttpStatus.OK);
    }
}
