package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.Banker;
import com.hanaro.wouldyouhana.dto.BankerSignUpDto;
import com.hanaro.wouldyouhana.forSignIn.JwtToken;
import com.hanaro.wouldyouhana.forSignIn.SecurityUtil;
import com.hanaro.wouldyouhana.forSignIn.dto.CustomerSignInDto;
import com.hanaro.wouldyouhana.forSignIn.dto.CustomerSignInReturnDTO;
import com.hanaro.wouldyouhana.repository.BankerRepository;
import com.hanaro.wouldyouhana.service.BankerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bankers")
public class BankerController {

    private final BankerService bankerService;
    private final BankerRepository bankerRepository;

    @PostMapping("/signIn")
    public ResponseEntity<CustomerSignInReturnDTO> signIn(@RequestBody CustomerSignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();
        JwtToken jwtToken = bankerService.signIn(email, password);
        log.info("request username = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        System.out.println("!!!!!!!!!!!!!!!!!!!"+ SecurityUtil.getCurrentUsername());
        String location = bankerService.getBranchName(jwtToken.getAccessToken());
        String nickName = bankerService.getiNickName(jwtToken.getAccessToken());
        CustomerSignInReturnDTO signInReturnDTO = new CustomerSignInReturnDTO(jwtToken.getAccessToken(), email, "B", location, nickName);
        return new ResponseEntity<>(signInReturnDTO, HttpStatus.CREATED);
    }

    // 회원가입 처리하는 API
    @PostMapping("/signUp")
    public ResponseEntity<?> signup(@RequestBody BankerSignUpDto signupRequest) {
        // 비밀번호 일치하는지 확인
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 이메일 중복 확인
        if (bankerRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }


        // Banker 엔티티로 변환
        Banker banker = Banker.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .name(signupRequest.getName())
                .branchName(signupRequest.getBranchName())
                .build();

        // 회원가입 처리
        Banker savedBanker = bankerService.registerBanker(banker);
        return new ResponseEntity<>(savedBanker, HttpStatus.CREATED);
    }

    // 이메일 인증 코드 전송
    @PostMapping("/banker-send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        String code = bankerService.sendEmailVerification(email);
        return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
    }

    // 이메일 인증 코드 검증
    @PostMapping("/banker-verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String code) {
        if (bankerService.verifyEmailCode(email, code)) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 일치하지 않습니다.");
        }
    }

}
