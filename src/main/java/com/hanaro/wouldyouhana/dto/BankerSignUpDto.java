package com.hanaro.wouldyouhana.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class BankerSignUpDto {
    private String name;
    private String email;
    private String confirmEmail; // 이메일 인증
    private String password;
    private String confirmPassword; // 비밀번호 확인
    private String branchName;
    private String login;
}
