package com.hanaro.wouldyouhana.controller;


import com.hanaro.wouldyouhana.dto.myPage.CustomerInfoResponseDTO;
import com.hanaro.wouldyouhana.dto.myPage.CustomerInfoUpdateDTO;
import com.hanaro.wouldyouhana.dto.myPage.InterestLocationRequestDTO;
import com.hanaro.wouldyouhana.service.CustomerMyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class CustomerMyPageController {

    private final CustomerMyPageService customerMyPageService;

    // 일반회원 관심지역 목록 불러오기 ("my/interestList")
    @GetMapping("/interestList")
    public ResponseEntity<List<String>> getInterestLocation(@RequestParam Long customer_id){

        List<String> location = customerMyPageService.getInterestLocation(customer_id);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }
    // 일반회원 관심지역 추가 ("my/add/interest")
    @PostMapping("/add/interest")
    public ResponseEntity<String> addInterestLocation(@RequestBody InterestLocationRequestDTO interestLocationRequestDTO){

        String result = customerMyPageService.addInterestLocation(interestLocationRequestDTO);

        return ResponseEntity.ok(result);
    }

    // 일반회원 관심지역 삭제
    @DeleteMapping("/delete/interest")
    public ResponseEntity<String> deleteInterestLocation(@RequestBody InterestLocationRequestDTO interestLocationRequestDTO){
        String result = customerMyPageService.deleteInterestLocation(interestLocationRequestDTO);;
        return ResponseEntity.ok(result);
    }

    // 일반회원 개인정보 수정 전 필드에 표시할 기존 정보 불러오기
    @GetMapping("/edit/info")
    public ResponseEntity<CustomerInfoResponseDTO> getPrevInfo(@RequestParam Long customerId) {
        CustomerInfoResponseDTO customerInfoResponseDTO = customerMyPageService.getInfoBeforeUpdateInfo(customerId);
        return new ResponseEntity<>(customerInfoResponseDTO, HttpStatus.OK);
    }

    // 일반회원 개인정보 수정 제출
    @PutMapping("/edit/info/submit")
    public ResponseEntity<String> editInfo(@RequestBody CustomerInfoUpdateDTO customerInfoUpdateDTO, @RequestParam Long customerId) {
        String result = customerMyPageService.updateCustomerInfo(customerInfoUpdateDTO, customerId);
        return ResponseEntity.ok(result);
    }
}
