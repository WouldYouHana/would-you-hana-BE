package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Banker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankerRepository extends JpaRepository<Banker, Long> {
    boolean existsByEmail(String email);
    Optional<Banker> findByEmail(String email);
    List<Banker> findByLocation(String location);
}

