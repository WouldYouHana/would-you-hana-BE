package com.hanaro.wouldyouhana.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerSignUpDto {
    private String nickname;
    private String email;
    private String password;
    private String confirmPassword; // 비밀번호 확인
    private String name;
    private String phone;
    private String location;
    private String gender;
    private String birthDate;
    private String login;
}
