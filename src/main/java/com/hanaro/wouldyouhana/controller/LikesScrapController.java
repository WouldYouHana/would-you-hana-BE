package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.LikesScrapRequestDTO;
import com.hanaro.wouldyouhana.dto.LikesScrapResponseDTO;
import com.hanaro.wouldyouhana.service.LikesScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("post/scrap")
    public ResponseEntity<String> scrapRequest(@RequestBody LikesScrapRequestDTO likesScrapRequestDTO){
        likesScrapService.saveScrap(likesScrapRequestDTO);
        return ResponseEntity.ok("Scrap Success");
    }


    /**
     * 좋아요 요청 (저장, 취소)
     * */
    @PostMapping("post/likes")
    public ResponseEntity<String> likesRequest(@RequestBody LikesScrapRequestDTO likesScrapRequestDTO){
        likesScrapService.saveLikes(likesScrapRequestDTO);
        return ResponseEntity.ok("Likes Success");
    }

    /**
     * 스크랩 게시글 조회 (최신순)
     * */
    @GetMapping("mypage/getScrap")
    public ResponseEntity<List<LikesScrapResponseDTO>> getScrap(@RequestBody Long customner_id){

        return ResponseEntity.ok(likesScrapService.getScrap(customner_id));
    }

    /**
     * 좋아요 게시글 조회 (최신순)
     * */
    @GetMapping("mypage/getLikes")
    public ResponseEntity<List<LikesScrapResponseDTO>> getLikes(@RequestBody Long customner_id){

        return ResponseEntity.ok(likesScrapService.getLikes(customner_id));
    }


}
