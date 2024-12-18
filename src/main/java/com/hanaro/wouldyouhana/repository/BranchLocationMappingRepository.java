package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.BranchLocationMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchLocationMappingRepository extends JpaRepository<BranchLocationMapping, Long> {
    Optional<BranchLocationMapping> findByBranchName(String branchName);
}
