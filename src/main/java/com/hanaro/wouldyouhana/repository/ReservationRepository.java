package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomerId(Long customerId);
    List<Reservation> findAllByBankerName(String bankerName);
}
