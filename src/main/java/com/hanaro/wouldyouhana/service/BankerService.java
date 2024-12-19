package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Banker;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.dto.banker.BankerListReturnDTO;
import com.hanaro.wouldyouhana.forSignIn.JwtToken;
import com.hanaro.wouldyouhana.forSignIn.JwtTokenProvider;
import com.hanaro.wouldyouhana.forSignIn.dto.CustomerSignInReturnDTO;
import com.hanaro.wouldyouhana.repository.BankerRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankerService {

    private final BankerRepository bankerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private EmailService emailService;
    private Map<String, String> verificationCodes = new HashMap<>();
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public BankerService(BankerRepository bankerRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.bankerRepository = bankerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
    }

    public Banker registerBanker(Banker banker) {
        // 비밀번호 암호화
        //banker.setPassword(passwordEncoder.encode(banker.getPassword()));
        banker.setPassword((banker.getPassword()));
        return bankerRepository.save(banker);
    }

    @Transactional
    public JwtToken signIn(String email, String password) {

        System.out.println("행원 로그인1");
        // 1. email + password 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println("행원 로그인2");

        // 2. 실제 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("행원 로그3");

        // 3. 인증 정보를 SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("행원 로그인4");

        // 4. JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(email, authentication);
        System.out.println("행원 로그인5");

        return jwtToken;
    }

    public List<BankerListReturnDTO> getBankerList(String location){
        // 지역에 해당하는 Banker 목록을 조회
        System.out.println("지역: "+ location);
        List<Banker> bankers = bankerRepository.findByLocation(location);

        // Banker -> BankerListReturnDTO로 변환
        return bankers.stream()
                .map(banker -> new BankerListReturnDTO(
                        banker.getId(),
                        banker.getName(),
                        banker.getBranchName(),
                        banker.getContent()))
                .collect(Collectors.toList());
    }


    public CustomerSignInReturnDTO getUserInfo(String token){
        // JWT 토큰에서 사용자 이메일을 추출
        String email = jwtTokenProvider.getUsernameFromToken(token);

        // 이메일로 행원을 조회
        Banker banker = bankerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

        Long id = banker.getId();
        String location = banker.getLocation();
        String name = banker.getName();
        String branchName = banker.getBranchName();

        return new CustomerSignInReturnDTO(token, id, email, "B", location, name,branchName);
    }



    public String sendEmailVerification(String email) {
        String code = emailService.sendVerificationCode(email);
        verificationCodes.put(email, code);
        return code;
    }

    public boolean verifyEmailCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }
}
