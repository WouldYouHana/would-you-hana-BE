package com.hanaro.wouldyouhana.forSignIn.customer;

import com.hanaro.wouldyouhana.forSignIn.JwtToken;
import com.hanaro.wouldyouhana.forSignIn.SecurityUtil;
import com.hanaro.wouldyouhana.forSignIn.dto.CustomerSignInDto;
import com.hanaro.wouldyouhana.forSignIn.dto.CustomerSignInReturnDTO;
import com.hanaro.wouldyouhana.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class CustomerControllerForSignIn {

    private final CustomerService customerService;

    @PostMapping("/signIn")
    public ResponseEntity<CustomerSignInReturnDTO> signIn(@RequestBody CustomerSignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();
        JwtToken jwtToken = customerService.signIn(email, password);
        log.info("request username = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        System.out.println("!!!!!!!!!!!!!!!!!!!"+ SecurityUtil.getCurrentUsername());

        CustomerSignInReturnDTO signInReturnDTO = customerService.getUserInfo(jwtToken.getAccessToken());
        return new ResponseEntity<>(signInReturnDTO, HttpStatus.CREATED);
    }

    @PostMapping("/test")
    public String test() {
        System.out.println("Accessing /bankers/test endpoint");
        System.out.println("!!!!!!!!!!!!!!!!!!!"+SecurityUtil.getCurrentUsername());
        return SecurityUtil.getCurrentUsername();
    }

    @PostMapping("/bankerTest")
    public String bankerTest() {
        System.out.println("Accessing /members/test endpoint");
        System.out.println("!!!!!!!!!!!!!!!!!!!"+SecurityUtil.getCurrentUsername());
        return SecurityUtil.getCurrentUsername();
    }

}
