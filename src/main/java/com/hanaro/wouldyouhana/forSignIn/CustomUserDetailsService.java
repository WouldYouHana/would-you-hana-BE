package com.hanaro.wouldyouhana.forSignIn;

import com.hanaro.wouldyouhana.domain.Customer;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        return customerRepository.findByEmail(useremail)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Customer customer) {
        return User.builder()
                .username(customer.getUsername())
                .password(passwordEncoder.encode(customer.getPassword()))
                //.password(customer.getPassword()) // 저장된 비밀번호를 그대로 사용
                .roles(customer.getRoles().toArray(new String[0]))
                .build();
    }

}
