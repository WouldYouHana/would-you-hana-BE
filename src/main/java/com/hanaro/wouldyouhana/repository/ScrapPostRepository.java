package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapPostRepository extends JpaRepository<ScrapPost, Long> {

    boolean existsByPostAndCustomer(Post post, Customer customer);
    Optional<ScrapPost> findByPostAndCustomer(Post post, Customer customer);
    List<ScrapPost> findByCustomer(Customer customer);
}
