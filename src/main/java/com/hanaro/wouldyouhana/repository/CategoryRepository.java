package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // categoryName으로 Category를 찾는 메서드
    Category findByName(String name);
}
