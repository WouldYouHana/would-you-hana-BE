package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.ScrapPost;
import com.hanaro.wouldyouhana.domain.ScrapQuestion;
import com.hanaro.wouldyouhana.dto.likesScrap.*;
import com.hanaro.wouldyouhana.service.LikesService;
import com.hanaro.wouldyouhana.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LikesScrapController {

    private final LikesService likesService;
    private final ScrapService scrapService;

    @Autowired
    public LikesScrapController(LikesService likesService, ScrapService scrapService) {
        this.likesService = likesService;
        this.scrapService = scrapService;
    }

    /**
     * 좋아요 요청 (저장, 취소)
     * */
    @PostMapping("post/like")
    public ResponseEntity<String> likesRequest(@RequestBody LikesRequestDTO likesRequestDTO){
        likesService.saveLikes(likesRequestDTO);
        return ResponseEntity.ok("Likes Success");
    }

    /**
     * 좋아요 게시글 조회 (최신순)
     * */
    @GetMapping("my/post/likeList/{customerId}")
    public ResponseEntity<List<LikesResponseDTO>> getLikes(@PathVariable Long customerId){

        return ResponseEntity.ok(likesService.getLikes(customerId));
    }

    /**
     * qna 스크랩 요청 (저장, 취소)
     * */
    @PostMapping("qna/scrap")
    public ResponseEntity<String> scrapQuestionRequest(@RequestBody ScrapQuestionRequestDTO scrapQuestionRequestDTO){
        scrapService.saveScrapForQuestion(scrapQuestionRequestDTO);
        return ResponseEntity.ok("Scrap Success");
    }

    /**
     * 커뮤니티 게시글 스크랩 요청 (저장, 취소)
     * */
    @PostMapping("post/scrap")
    public ResponseEntity<String> scrapPostRequest(@RequestBody ScrapPostRequestDTO scrapPostRequestDTO){
        scrapService.saveScrapForPost(scrapPostRequestDTO);
        return ResponseEntity.ok("Scrap Success");
    }


    /**
     * 스크랩한 qna 조회 (최신순)
     * */
    @GetMapping("my/qna/scrapList/{customerId}")
    public ResponseEntity<List<LikesScrapResponseDTO>> getQuestionScrap(@PathVariable Long customerId){
        return ResponseEntity.ok(scrapService.getScrapForQuestion(customerId));
    }


    /**
     * 스크랩한 커뮤니티 게시글 조회 (최신순)
     * */
    @GetMapping("my/post/scrapList/{customerId}")
    public ResponseEntity<List<LikesScrapResponseDTO>> getPostScrap(@PathVariable Long customerId){

        return ResponseEntity.ok(scrapService.getScrapForPost(customerId));
    }




}
