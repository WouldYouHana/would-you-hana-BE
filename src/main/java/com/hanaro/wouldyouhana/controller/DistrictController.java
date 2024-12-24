package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.question.QnaListDTO;
import com.hanaro.wouldyouhana.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/district")
public class DistrictController {

    private final QuestionService questionService;

    // 지역구별 가장 최근에 작성된 qna 3개 반환
    // /district/{지역구}/recentQna
    @GetMapping("/{location}/recentQna")
    public ResponseEntity<List<QnaListDTO>> getLatestBranchQuestions(@PathVariable String location) {
        List<QnaListDTO> questionList = questionService.get3QuestionsSortedByLatest(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }
}
