package com.hanaro.wouldyouhana.dto.banker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BankerMyPageReturnDTO {

    private String name; //행원 이름
    private List<String> specializations; //전문분야
    private String content; //소개글
    private String filePath;
    private Long totalGoodCount;
    private Long totalCommentCount;
    private Long totalViewCount;

}
