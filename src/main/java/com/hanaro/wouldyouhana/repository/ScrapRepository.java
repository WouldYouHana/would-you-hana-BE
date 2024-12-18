package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    boolean existsByQuestionAndCustomer(Question question, Customer customer);
    Optional<Scrap> findByQuestionAndCustomer(Question question, Customer customer);
    List<Scrap> findByCustomer(Customer customer);
}
