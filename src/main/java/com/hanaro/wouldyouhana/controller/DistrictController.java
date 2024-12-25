package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.post.PostListDTO;
import com.hanaro.wouldyouhana.dto.question.QnaListDTO;
import com.hanaro.wouldyouhana.service.PostService;
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
    private final PostService postService;


    // 지역구별 가장 최근에 작성된 qna 3개 반환
    // /district/{지역구}/recentQna
    @GetMapping("/{location}/recentQna")
    public ResponseEntity<List<QnaListDTO>> getLatest3Questions(@PathVariable String location) {
        List<QnaListDTO> questionList = questionService.get3QuestionsSortedByLatest(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 지역구별 조회수 많은 (커뮤니티)게시물 3개 반환
    // /district/{지역구}/hotPost
    @GetMapping("/{location}/hotPost")
    public ResponseEntity<List<PostListDTO>> getLatest3Post(@PathVariable String location) {
        List<PostListDTO> questionList = postService.get3PostSortedByLatest(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 지역구 랜딩페이지에서 검색 -> 지역구 qna 페이지에서 검색
    // /district/{지역구}/{searchTerm}
    @GetMapping("/qna/{location}/{searchTerm}")
    public ResponseEntity<List<QnaListDTO>> searchTermFromQuestion(@PathVariable String location,
                                                                   @PathVariable String searchTerm){
        List<QnaListDTO> questionList = questionService.searchTermFromQuestion(location, searchTerm);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

}
