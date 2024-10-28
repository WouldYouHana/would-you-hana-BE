package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Banker;
import com.hanaro.wouldyouhana.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankerRepository extends JpaRepository<Banker, Long> {
}
