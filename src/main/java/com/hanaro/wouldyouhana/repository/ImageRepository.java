package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // 특정 question_id에 해당하는 모든 이미지를 삭제하는 메서드
    void deleteAllByQuestion_Id(Long questionId);

    List<Image> findByQuestion_Id(Long questionId);

    List<Image> findByPost_Id(Long postId);
    void deleteAllByPost_Id(Long postId);
}
