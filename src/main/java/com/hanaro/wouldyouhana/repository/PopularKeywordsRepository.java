package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.PopularKeywords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopularKeywordsRepository extends JpaRepository<PopularKeywords, Long> {
    List<PopularKeywords> findAllByLocation(String location);
}
