package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Reservation;
import com.hanaro.wouldyouhana.dto.reservation.ReservationRequestDTO;
import com.hanaro.wouldyouhana.dto.reservation.ReservationResponseDTO;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    // 예약 등록
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

    // 고객의 예약 목록 확인
    public List<ReservationResponseDTO> getAllReservations(Long customerId) {

        List<Reservation> foundReservations = reservationRepository.findAllByCustomerId(customerId);
        List<ReservationResponseDTO> reservationResponseDTOS = foundReservations.stream().map((reservation) -> {
            ReservationResponseDTO responseDTO = new ReservationResponseDTO();
            responseDTO.setBranchName(reservation.getBranchName());
            responseDTO.setRdayTime(reservation.getRdayTime());
            return responseDTO;
        }).collect(Collectors.toList());
        return reservationResponseDTOS;
    }

}
