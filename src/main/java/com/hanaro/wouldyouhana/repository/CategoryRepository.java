package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
