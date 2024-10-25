package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
}
