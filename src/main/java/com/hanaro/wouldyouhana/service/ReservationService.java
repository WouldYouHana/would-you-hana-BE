package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Banker;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Reservation;
import com.hanaro.wouldyouhana.dto.reservation.ReservationRequestDTO;
import com.hanaro.wouldyouhana.dto.reservation.ReservationResponseDTO;
import com.hanaro.wouldyouhana.repository.BankerRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final BankerRepository bankerRepository;

    public ReservationService(CustomerRepository customerRepository, ReservationRepository reservationRepository, BankerRepository bankerRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.bankerRepository = bankerRepository;
    }

    // 예약 등록
    public Long makeReservation(ReservationRequestDTO reservationRequestDTO) {
        Customer customer = customerRepository.findById(reservationRequestDTO.getCustomerId()).get();

        Reservation newReservation = Reservation.builder()
                .customer(customer)
                .branchName(reservationRequestDTO.getBranchName())
                .rdayTime(reservationRequestDTO.getReservationDate())
                .bankerName(reservationRequestDTO.getBankerName())
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
            responseDTO.setBankerName(reservation.getBankerName());
            responseDTO.setRdayTime(reservation.getRdayTime());
            return responseDTO;
        }).collect(Collectors.toList());
        return reservationResponseDTOS;
    }

    // 행원 앞으로 신청된 예약 목록 확인
    public List<ReservationResponseDTO> getAllReservationsForBanker(Long bankerId) {
        Banker foundBanker = bankerRepository.findById(bankerId).get();
        String bankerName = foundBanker.getName();
        List<Reservation> foundReservations = reservationRepository.findAllByBankerName(bankerName);
        List<ReservationResponseDTO> reservationResponseDTOS = foundReservations.stream().map((reservation) -> {
            ReservationResponseDTO responseDTO = new ReservationResponseDTO();
            responseDTO.setBranchName(reservation.getBranchName());
            responseDTO.setBankerName(reservation.getBankerName());
            responseDTO.setRdayTime(reservation.getRdayTime());
            return responseDTO;
        }).collect(Collectors.toList());
        return reservationResponseDTOS;
    }

}
