package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.likesScrap.LikesScrapRequestDTO;
import com.hanaro.wouldyouhana.dto.likesScrap.LikesScrapResponseDTO;
import com.hanaro.wouldyouhana.service.LikesScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LikesScrapController {

    private final LikesScrapService likesScrapService;

    @Autowired
    public LikesScrapController(LikesScrapService likesScrapService) {
        this.likesScrapService = likesScrapService;
    }

    /**
     * 스크랩 요청 (저장, 취소)
     * */
//    @PostMapping("qna/scrap")
//    public ResponseEntity<String> scrapRequest(@RequestBody LikesScrapRequestDTO likesScrapRequestDTO){
//        likesScrapService.saveScrap(likesScrapRequestDTO);
//        return ResponseEntity.ok("Scrap Success");
//    }


    /**
     * 좋아요 요청 (저장, 취소)
     * */
//    @PostMapping("post/likes")
//    public ResponseEntity<String> likesRequest(@RequestBody LikesScrapRequestDTO likesScrapRequestDTO){
//        likesScrapService.saveLikes(likesScrapRequestDTO);
//        return ResponseEntity.ok("Likes Success");
//    }

    /**
     * 스크랩 게시글 조회 (최신순)
     * */
    @GetMapping("mypage/getScrap/{customerId}")
    public ResponseEntity<List<LikesScrapResponseDTO>> getScrap(@PathVariable Long customerId){

        return ResponseEntity.ok(likesScrapService.getScrap(customerId));
    }

    /**
     * 좋아요 게시글 조회 (최신순)
     * */
//    @GetMapping("mypage/getLikes/{customerId}")
//    public ResponseEntity<List<LikesScrapResponseDTO>> getLikes(@PathVariable Long customerId){
//
//        return ResponseEntity.ok(likesScrapService.getLikes(customerId));
//    }


}
