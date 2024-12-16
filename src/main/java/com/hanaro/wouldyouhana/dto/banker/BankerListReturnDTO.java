package com.hanaro.wouldyouhana.dto.banker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankerListReturnDTO {

    private Long bankerId;
    private String bankerName;
    private String branchName;
    private String content;
}
