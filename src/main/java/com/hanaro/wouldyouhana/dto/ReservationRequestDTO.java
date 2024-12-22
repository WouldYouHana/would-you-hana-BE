package com.hanaro.wouldyouhana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservationRequestDTO {

    private Long customerId;
    private String branchName;
    private LocalDateTime reservationDate;
}
