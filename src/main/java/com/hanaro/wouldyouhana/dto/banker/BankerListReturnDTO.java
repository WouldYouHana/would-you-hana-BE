package com.hanaro.wouldyouhana.dto.banker;

import com.hanaro.wouldyouhana.domain.Specialization;
import com.hanaro.wouldyouhana.dto.SpecializationResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BankerListReturnDTO {

    private Long bankerId;
    private String bankerName;
    private String branchName;
    private String content;
    private String filePath;
    private List<SpecializationResponseDTO> specializations;
}
