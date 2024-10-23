package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
