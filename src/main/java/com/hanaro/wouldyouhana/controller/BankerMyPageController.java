package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.banker.BankerMyPageReturnDTO;
import com.hanaro.wouldyouhana.service.BankerMyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class BankerMyPageController {

    private final BankerMyPageService bankerMyPageService;

    @GetMapping("/bankers/mypage")
    public ResponseEntity<BankerMyPageReturnDTO> getBankerMyPage(@RequestParam Long banker_id){

        BankerMyPageReturnDTO bankerMyPage = bankerMyPageService.getBankerMyPage(banker_id);
        return new ResponseEntity<>(bankerMyPage, HttpStatus.OK);
    }
}
