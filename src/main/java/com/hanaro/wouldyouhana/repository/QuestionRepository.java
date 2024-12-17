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

    // location을 기준으로 최신순 (createdAt)으로 질문 목록을 가져오는 메서드
    @Query("SELECT q FROM Question q WHERE q.location = :location ORDER BY q.createdAt DESC")
    List<Question> findByLocationOrderByCreatedAtDesc(String location);

    // location을 기준으로 좋아요 순 (likeCount)으로 질문 목록을 가져오는 메서드
    @Query("SELECT q FROM Question q WHERE q.location = :location ORDER BY q.likeCount DESC")
    List<Question> findByLocationOrderByLikeCountDesc(String location);

    // location을 기준으로 질문 목록을 가져오는 메서드
    List<Question> findByLocation(String location);
    // location과 categoryId를 기준으로 질문 목록을 가져오는 메서드
    List<Question> findByLocationAndCategory_Id(String location, Long categoryId);

    // 오늘 날짜의 시작 시간과 끝 시간을 계산하여 조회
    @Query("SELECT q FROM Question q WHERE q.location = :location AND q.createdAt >= :startOfDay AND q.createdAt < :endOfDay ORDER BY q.viewCount DESC")
    List<Question> findTop6ByCreatedAtTodayOrderByViewCountDesc(String location, LocalDateTime startOfDay, LocalDateTime endOfDay, PageRequest pageable);
}
