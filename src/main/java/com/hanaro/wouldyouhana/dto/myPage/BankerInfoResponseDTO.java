package com.hanaro.wouldyouhana.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BankerInfoResponseDTO {
    private String bankerName;
    private String bankerEmail;
    private String branchName;
}
