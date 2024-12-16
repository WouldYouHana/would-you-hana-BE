package com.hanaro.wouldyouhana.dto.banker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankerSignInReturnDTO {

    private String token;
    private String email;
    private String role;
    private String branchName;
}
