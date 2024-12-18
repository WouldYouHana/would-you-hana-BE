package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
