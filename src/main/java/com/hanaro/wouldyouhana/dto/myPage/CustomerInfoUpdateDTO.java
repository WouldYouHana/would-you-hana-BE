package com.hanaro.wouldyouhana.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerInfoUpdateDTO {

    private String password;
    private String nickname;
    private String birthDate;
    private String gender;
    private String location;
    private String phone;
}
