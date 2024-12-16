package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Question;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<List<Question>> findByCategory_id(Long categoryId);
    Optional<List<Question>> findByCustomerId(Long customerId);

    // 오늘 날짜의 시작 시간과 끝 시간을 계산하여 조회
    @Query("SELECT q FROM Question q WHERE q.createdAt >= :startOfDay AND q.createdAt < :endOfDay ORDER BY q.viewCount DESC")
    List<Question> findTop6ByCreatedAtTodayOrderByViewCountDesc(LocalDateTime startOfDay, LocalDateTime endOfDay, PageRequest pageable);
}
