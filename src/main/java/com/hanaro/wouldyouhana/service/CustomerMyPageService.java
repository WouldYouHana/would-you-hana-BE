package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Location;
import com.hanaro.wouldyouhana.dto.myPage.CustomerInfoResponseDTO;
import com.hanaro.wouldyouhana.dto.myPage.CustomerInfoUpdateDTO;
import com.hanaro.wouldyouhana.dto.myPage.InterestLocationRequestDTO;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CustomerMyPageService {

    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;

    // 일반회원 관심지역 목록 불러오기
    public List<String> getInterestLocation(Long customer_id) {
        List<Location> location = locationRepository.findByCustomer_Id(customer_id);
        List<String> returnLocation = new ArrayList<>();  // 빈 리스트로 초기화

        for (Location l : location) {
            returnLocation.add(l.getLocation());
        }

        return returnLocation;
    }

    public String addInterestLocation(InterestLocationRequestDTO interestLocationRequestDTO){

        Optional<Customer> optionalCustomer = customerRepository.findById(interestLocationRequestDTO.getCustomerId());

        // Optional이 비어 있지 않으면 Customer 객체를 가져와 Location 객체 생성
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();  // Optional에서 Customer 추출
            Location location = new Location(customer, interestLocationRequestDTO.getLocation());
            locationRepository.save(location);
            return "Location added successfully";
        }

        // Optional이 비어 있는 경우 처리 (예: 고객이 존재하지 않으면 에러 메시지 반환)
        return "Customer not found";
    }

    public String deleteInterestLocation(InterestLocationRequestDTO interestLocationRequestDTO){
        Optional<Customer> optionalCustomer = customerRepository.findById(interestLocationRequestDTO.getCustomerId());

        // Optional이 비어 있지 않으면 Customer 객체를 가져와 Location 객체 생성
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();  // Optional에서 Customer 추출

            // 해당 Customer와 일치하는 Location 조회
            Location location = locationRepository.findByCustomerAndLocation(customer, interestLocationRequestDTO.getLocation());

            if (location != null) {
                // Location 객체가 존재하면 삭제
                locationRepository.delete(location);
                return "Location deleted successfully";
            } else {
                return "Location not found";
            }
        }

        return "Customer not found";
    }

    // 일반회원 개인정보 수정 전 필드에 데이터 채우기
    public CustomerInfoResponseDTO getInfoBeforeUpdateInfo(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new EntityNotFoundException("Customer not found"));

        CustomerInfoResponseDTO info = CustomerInfoResponseDTO.builder()
                .customerName(customer.getName())
                .customerEmail(customer.getEmail())
                .nickname(customer.getNickname())
                .birthDate(customer.getBirthDate())
                .gender(customer.getGender())
                .location(customer.getLocation())
                .phone(customer.getPhone())
                .build();

        return info;
    }

    // 일반회원 개인정보 수정 제출
    public String updateCustomerInfo(CustomerInfoUpdateDTO customerInfoUpdateDTO, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new EntityNotFoundException("Customer not found"));

        customer.setPassword(customerInfoUpdateDTO.getPassword());
        customer.setNickname(customerInfoUpdateDTO.getNickname());
        customer.setBirthDate(customerInfoUpdateDTO.getBirthDate());
        customer.setGender(customerInfoUpdateDTO.getGender());
        customer.setLocation(customerInfoUpdateDTO.getLocation());
        customer.setPhone(customerInfoUpdateDTO.getPhone());

        customerRepository.save(customer);
        return "Customer updated successfully";


    }

}
