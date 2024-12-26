package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Comment;
import com.hanaro.wouldyouhana.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByQuestion(Question question);

    // 지역구(location)별로 오늘 작성된 댓글을 기준으로 유저별 댓글 수를 구함
    @Query("SELECT c.customer.id AS customerId, COUNT(c) AS commentCount " +
            "FROM Comment c " +
            "JOIN c.question q " + // 질문과 연관된 지역구를 찾기 위해 join
            "WHERE q.location = :location " +
            "AND c.createdAt >= :startOfDay " + // 오늘 날짜 기준 필터링
            "GROUP BY c.customer.id " +
            "ORDER BY commentCount DESC")
    List<Object[]> findTop3CommentingUsers(@Param("location") String location,
                                           @Param("startOfDay") LocalDateTime startOfDay);
}
