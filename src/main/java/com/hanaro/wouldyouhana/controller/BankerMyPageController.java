package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.banker.BankerMyPageReturnDTO;
import com.hanaro.wouldyouhana.dto.banker.BankerProfileModifyDTO;
import com.hanaro.wouldyouhana.dto.myPage.BankerInfoResponseDTO;
import com.hanaro.wouldyouhana.dto.myPage.BankerInfoUpdateDTO;
import com.hanaro.wouldyouhana.dto.reservation.ReservationResponseDTO;
import com.hanaro.wouldyouhana.service.BankerMyPageService;
import com.hanaro.wouldyouhana.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class BankerMyPageController {

    private final BankerMyPageService bankerMyPageService;
    private final ReservationService reservationService;

    // 행원 프로필 조회
    @GetMapping("/bankers/mypage")
    public ResponseEntity<BankerMyPageReturnDTO> getBankerMyPage(@RequestParam Long bankerId){

        BankerMyPageReturnDTO bankerMyPage = bankerMyPageService.getBankerMyPage(bankerId);
        return new ResponseEntity<>(bankerMyPage, HttpStatus.OK);
    }

    // 행원 프로필 수정
    @GetMapping("/bankers/mypage/modifyProfile")
    public ResponseEntity<String> modifyBankerProfile(@RequestPart("profile") BankerProfileModifyDTO modifyInfoDTO,
                                                      @RequestPart(value="file", required=false) MultipartFile file) {
        String result = bankerMyPageService.modifyBankerProfile(modifyInfoDTO, file);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 행원 개인정보 수정 전 필드에 표시할 기존 정보 불러오기
    @GetMapping("/bankers/edit/info")
    public ResponseEntity<BankerInfoResponseDTO> getPrevInfo(@RequestParam Long bankerId){
        BankerInfoResponseDTO bankerInfoResponseDTO = bankerMyPageService.getInfoBeforeUpdateInfo(bankerId);
        return new ResponseEntity<>(bankerInfoResponseDTO, HttpStatus.OK);
    }

    // 행원 개인정보 수정 제출
    @PutMapping("/bankers/edit/info/submit")
    public ResponseEntity<String> editInfo(@RequestBody BankerInfoUpdateDTO bankerInfoUpdateDTO, @RequestParam Long bankerId){
        String result = bankerMyPageService.updateBankerInfo(bankerInfoUpdateDTO, bankerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 행원 앞으로 신청된 예약 목록
    @GetMapping("/bankers/reservations")
    public ResponseEntity<List<ReservationResponseDTO>> getReservations(@RequestParam Long bankerId){
        List<ReservationResponseDTO> foundReservations = reservationService.getAllReservationsForBanker(bankerId);
        return new ResponseEntity<>(foundReservations, HttpStatus.OK);
    }
}
