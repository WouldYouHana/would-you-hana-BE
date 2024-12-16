package com.hanaro.wouldyouhana.forSignIn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomerSignInDto {

    private String email;
    private String password;

}
