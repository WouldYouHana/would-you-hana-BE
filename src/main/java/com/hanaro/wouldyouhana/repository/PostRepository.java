package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByLocation(String location);

    List<Post> findByLocationAndCategoryId(String location, Long categoryId);
}
