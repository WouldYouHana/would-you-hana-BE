package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomerId(Long customerId);
    List<Reservation> findAllByBankerName(String bankerName);
    @Query("SELECT r FROM Reservation r WHERE r.bankerName IS NULL AND r.branchName = :branchName")
    List<Reservation> findAllByBranchAndNoBanker(String branchName);
}
