package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer registerCustomer(Customer customer) {
        // 비밀번호 암호화
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }
}
