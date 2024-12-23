package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.reservation.ReservationRequestDTO;
import com.hanaro.wouldyouhana.dto.reservation.ReservationResponseDTO;
import com.hanaro.wouldyouhana.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 등록
    @PostMapping("/register")
    public ResponseEntity<Long> makeReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Long reservationId = reservationService.makeReservation(reservationRequestDTO);
        return new ResponseEntity<>(reservationId, HttpStatus.OK);
    }

}
