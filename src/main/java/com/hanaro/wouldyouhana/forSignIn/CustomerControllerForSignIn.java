package com.hanaro.wouldyouhana.forSignIn;

import com.hanaro.wouldyouhana.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();
        JwtToken jwtToken = customerService.signIn(email, password);
        log.info("request username = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        System.out.println("!!!!!!!!!!!!!!!!!!!"+SecurityUtil.getCurrentUsername());
        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
        System.out.println("Accessing /members/test endpoint");
        System.out.println("!!!!!!!!!!!!!!!!!!!"+SecurityUtil.getCurrentUsername());
        return SecurityUtil.getCurrentUsername();

    }

}
