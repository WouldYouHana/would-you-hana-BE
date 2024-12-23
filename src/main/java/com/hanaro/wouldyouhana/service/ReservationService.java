package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Reservation;
import com.hanaro.wouldyouhana.dto.ReservationRequestDTO;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    public Long makeReservation(ReservationRequestDTO reservationRequestDTO) {
        Customer customer = customerRepository.findById(reservationRequestDTO.getCustomerId()).get();

        Reservation newReservation = Reservation.builder()
                .customer(customer)
                .branchName(reservationRequestDTO.getBranchName())
                .rdayTime(reservationRequestDTO.getReservationDate())
                .build();

        Reservation reservation = reservationRepository.save(newReservation);
        return reservation.getId();
    }
}
