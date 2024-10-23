package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
