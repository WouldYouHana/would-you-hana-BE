package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByLocation(String location);

    List<Post> findByLocationAndCategoryId(String location, Long categoryId);

    // location을 기준으로 최신순 (createdAt)으로 질문 목록을 가져오는 메서드
    List<Post> findByLocationOrderByViewCountDesc(String location);
}
