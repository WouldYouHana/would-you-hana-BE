package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Location;
import com.hanaro.wouldyouhana.forSignIn.JwtToken;
import com.hanaro.wouldyouhana.forSignIn.JwtTokenProvider;
import com.hanaro.wouldyouhana.forSignIn.dto.CustomerSignInReturnDTO;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.LocationRepository;
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
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private EmailService emailService;
    private Map<String, String> verificationCodes = new HashMap<>();

    public CustomerService(CustomerRepository customerRepository, LocationRepository locationRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.customerRepository = customerRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
    }

    public Customer registerCustomer(Customer customer) {
        // 비밀번호 암호화
        //customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setPassword((customer.getPassword()));

        Customer saveCustomer = customerRepository.save(customer);

        Location location = new Location(saveCustomer, saveCustomer.getLocation());
        locationRepository.save(location);

        return saveCustomer;
    }

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public JwtToken signIn(String email, String password) {
        // 1. email + password 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(email, authentication);

        return jwtToken;
    }

    public CustomerSignInReturnDTO getUserInfo(String token){
        // JWT 토큰에서 사용자 이메일을 추출
        String email = jwtTokenProvider.getUsernameFromToken(token);

        // 이메일로 고객을 조회
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

        Long id = customer.getId();
        String location = customer.getLocation();
        String name = customer.getName();
        String branchName = "";

        // 관심 있는 위치들에서 location String만 추출
        List<String> interestLocationStrings = locationRepository.findByCustomer_Id(id)
                .stream()
                .map(Location::getLocation)  // 각 Location 객체의 location 필드를 추출
                .collect(Collectors.toList());  // 리스트로 변환

        return new CustomerSignInReturnDTO(token, id, email, "C", location, name,branchName, interestLocationStrings);
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
