package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Answer;
import com.hanaro.wouldyouhana.domain.AnswerGood;
import com.hanaro.wouldyouhana.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerGoodRepository extends JpaRepository<AnswerGood, Long> {

    boolean existsByAnswerAndCustomer(Answer answer, Customer customer);
    AnswerGood findByAnswerAndCustomer(Answer answer, Customer customer);
}
