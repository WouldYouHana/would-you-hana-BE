package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionId(Long questionId);
}
