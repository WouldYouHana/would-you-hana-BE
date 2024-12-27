package com.hanaro.wouldyouhana.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReservationResponseDTO {

    private String customerName;
    private String nickName;
    private String branchName;
    private LocalDateTime rdayTime;
    private String bankerName;
}
