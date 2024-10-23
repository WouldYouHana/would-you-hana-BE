package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
