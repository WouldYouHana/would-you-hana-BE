package com.hanaro.wouldyouhana.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
// 수정 페이지 접근했을 때 정보 미리 채워놓기
public class CustomerInfoResponseDTO {

    // 수정 불가능 필드
    private String customerName;
    private String customerEmail;

    // 수정 가능 필드
    private String nickname;
    private String birthDate;
    private String gender;
    private String location;
    private String phone;
}
