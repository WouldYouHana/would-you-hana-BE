package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // 특정 question_id에 해당하는 모든 이미지를 삭제하는 메서드
    void deleteAllByQuestionId(Long questionId);
}
