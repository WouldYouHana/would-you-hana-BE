package com.hanaro.wouldyouhana.forSignIn.customer;

import com.hanaro.wouldyouhana.domain.Banker;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.repository.BankerRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final BankerRepository bankerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
//        return bankerRepository.findByEmail(useremail)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
//        return customerRepository.findByEmail(useremail)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));

        // 1. bankerRepository에서 먼저 검색
        UserDetails banker = bankerRepository.findByEmail(useremail)
                .map(this::createUserDetails)
                .orElse(null);  // 만약 없으면 null 반환

        // 2. 만약 banker를 찾지 못했다면, customerRepository에서 검색
        if (banker == null) {
            // 고객도 찾을 수 없다면 예외 발생
            return customerRepository.findByEmail(useremail)
                    .map(this::createUserDetails)
                    .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
        }

        // 만약 banker를 찾았다면 바로 반환
        return banker;
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Customer customer) {
        return User.builder()
                .username(customer.getUsername())
                .password(passwordEncoder.encode(customer.getPassword()))
//                .password(customer.getPassword()) // 저장된 비밀번호를 그대로 사용
                .roles(customer.getRoles().toArray(new String[0]))
                .build();
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Banker banker) {
        return User.builder()
                .username(banker.getUsername())
                .password(passwordEncoder.encode(banker.getPassword()))
//                .password(customer.getPassword()) // 저장된 비밀번호를 그대로 사용
                .roles(banker.getRoles().toArray(new String[0]))
                .build();
    }

}
