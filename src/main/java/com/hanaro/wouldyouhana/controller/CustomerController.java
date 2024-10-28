package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.dto.CustomerSignUpDto;
import com.hanaro.wouldyouhana.forSignIn.JwtToken;
import com.hanaro.wouldyouhana.forSignIn.SecurityUtil;
import com.hanaro.wouldyouhana.forSignIn.SignInDto;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    // 회원가입 처리하는 API
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CustomerSignUpDto signupRequest) {
        // 비밀번호 일치하는지 확인
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 이메일 중복 확인
        if (customerRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }


        // Customer 엔티티로 변환
        Customer customer = Customer.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .name(signupRequest.getName())
                .nickname(signupRequest.getNickname())
                .phone(signupRequest.getPhone())
                .location(signupRequest.getLocation())
                .gender(signupRequest.getGender())
                .birthDate(signupRequest.getBirthDate())
                .experiencePoints(0L) // 초기 경험치 0 설정
                .createdAt(LocalDateTime.now()) // 현재 시간으로 가입일자 설정
                .build();

        // 회원가입 처리
        Customer savedCustomer = customerService.registerCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

}
