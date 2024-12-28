package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.ScrapPost;
import com.hanaro.wouldyouhana.domain.ScrapQuestion;
import com.hanaro.wouldyouhana.dto.likesScrap.*;
import com.hanaro.wouldyouhana.service.LikesService;
import com.hanaro.wouldyouhana.service.ScrapService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LikesScrapController {

    private final LikesService likesService;
    private final ScrapService scrapService;

    /**
     * 좋아요 요청 (저장, 취소)
     * */
    @PostMapping("/post/dolike")
    public ResponseEntity<Long> likesRequest(@RequestBody LikesRequestDTO likesRequestDTO){
        Long likeCount = likesService.saveLikes(likesRequestDTO);
        return ResponseEntity.ok(likeCount);
    }

    /**
     * 좋아요 게시글 조회 (최신순)
     * */
    @GetMapping("/my/post/likeList/{customerId}")
    public ResponseEntity<List<LikesResponseDTO>> getLikes(@PathVariable Long customerId){

        return ResponseEntity.ok(likesService.getLikes(customerId));
    }

    /**
     * qna 스크랩 요청 (저장, 취소)
     * */
    @PostMapping("/qna/scrap")
    public ResponseEntity<String> scrapQuestionRequest(@RequestBody ScrapQuestionRequestDTO scrapQuestionRequestDTO){
        scrapService.saveScrapForQuestion(scrapQuestionRequestDTO);
        return ResponseEntity.ok("Scrap Success");
    }

    /**
     * 커뮤니티 게시글 스크랩 요청 (저장, 취소)
     * */
    @PostMapping("/post/scrap")
    public ResponseEntity<String> scrapPostRequest(@RequestBody ScrapPostRequestDTO scrapPostRequestDTO){
        scrapService.saveScrapForPost(scrapPostRequestDTO);
        return ResponseEntity.ok("Scrap Success");
    }


    /**
     * 스크랩한 qna 조회 (최신순)
     * */
    @GetMapping("/my/qna/scrapList/{customerId}")
    public ResponseEntity<List<ScrapQuestionResponseDTO>> getQuestionScrap(@PathVariable Long customerId){
        return ResponseEntity.ok(scrapService.getScrapForQuestion(customerId));
    }


    /**
     * 스크랩한 커뮤니티 게시글 조회 (최신순)
     * */
    @GetMapping("/my/post/scrapList/{customerId}")
    public ResponseEntity<List<ScrapPostResponseDTO>> getPostScrap(@PathVariable Long customerId){

        return ResponseEntity.ok(scrapService.getScrapForPost(customerId));
    }


    // 게시글, qna 찾기 실패시 예외 처리
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        // 에러 메시지를 반환하거나 커스터마이징하여 응답
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * 커뮤니티 특정 게시글 스크랩 여부 조회
     * */
    @GetMapping("/my/qna/scrap/{customerId}/{questionId}")
    public ResponseEntity<Boolean> getIsQuestionScrapChecked(@PathVariable Long customerId, @PathVariable Long questionId){

        return ResponseEntity.ok(scrapService.isQuestionScrapChecked(customerId, questionId));
    }

    /**
     * 커뮤니티 특정 게시글 스크랩 여부 조회
     * */
    @GetMapping("/my/post/scrap/{customerId}/{postId}")
    public ResponseEntity<Boolean> getIsPostScrapChecked(@PathVariable Long customerId, @PathVariable Long postId){

        return ResponseEntity.ok(scrapService.isPostScrapChecked(customerId, postId));
    }



}
