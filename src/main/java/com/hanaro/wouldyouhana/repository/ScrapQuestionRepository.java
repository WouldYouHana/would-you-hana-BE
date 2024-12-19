package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.domain.ScrapPost;
import com.hanaro.wouldyouhana.domain.ScrapQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapQuestionRepository extends JpaRepository<ScrapQuestion, Long> {

    boolean existsByQuestionAndCustomer(Question question, Customer customer);
    Optional<ScrapQuestion> findByQuestionAndCustomer(Question question, Customer customer);
    List<ScrapQuestion> findByCustomer(Customer customer);
}
