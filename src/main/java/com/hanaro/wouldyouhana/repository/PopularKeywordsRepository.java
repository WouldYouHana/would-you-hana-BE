package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopularKeywordsRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findAllByLocation(String location);
}
