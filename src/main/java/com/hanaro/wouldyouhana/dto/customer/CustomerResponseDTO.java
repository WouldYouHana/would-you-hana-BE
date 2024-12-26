package com.hanaro.wouldyouhana.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

    //프로필사진, 닉네임, 경험치, 댓글수, 게시글수(Qna+커뮤니티)
    private String filepath;
    private String nickname;
    private Long experiencePoints;
    private Long todayCommentCount; //오늘 작성한 댓글 수
    private Long QnaPostCount; // 오늘 작성한 게시글수(Qna+커뮤니티)
}
