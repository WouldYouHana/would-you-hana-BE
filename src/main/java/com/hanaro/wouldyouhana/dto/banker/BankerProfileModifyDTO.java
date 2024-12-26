package com.hanaro.wouldyouhana.dto.banker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BankerProfileModifyDTO {

    private Long bankerId;
    private List<String> specializations;
    private String content;
    private String filePath;

}
