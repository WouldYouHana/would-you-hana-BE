package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
