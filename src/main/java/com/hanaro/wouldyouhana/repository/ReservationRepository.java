package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
