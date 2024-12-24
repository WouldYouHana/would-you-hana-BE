package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c.nickname FROM Customer c WHERE c.id = :customerId")
    String getNicknameById(@Param("customerId") Long customerId);

    boolean existsByNickname(String nickname);
}

