package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Answer;
import com.hanaro.wouldyouhana.domain.Banker;
import com.hanaro.wouldyouhana.domain.Specialization;
import com.hanaro.wouldyouhana.dto.banker.BankerMyPageReturnDTO;
import com.hanaro.wouldyouhana.repository.BankerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankerMyPageService {

    private final BankerRepository bankerRepository;


    /*
    *
    private String name; //행원 이름
    private List<String> specializations; //전문분야
    private String content; //소개글
    private Long totalGoodCount;
    private Long totalCommentCount;
    private Long totalViewCount;
    * */
    public BankerMyPageReturnDTO getBankerMyPage(Long banker_id){

        Optional<Banker> optionalBanker = bankerRepository.findById(banker_id);

        if (optionalBanker.isPresent()) {
            Banker banker = optionalBanker.get();

            List<String> specializations = new ArrayList<>();
            for(Specialization s : banker.getSpecializations()){
                specializations.add(s.getName());
            }
            // 계산하는 로직 추가 필요
            Long totalGoodCount = calTotalGoodCount(banker);
            Long totalCommentCount = 0L;
            Long totalViewCount = banker.getViewCount();

            return new BankerMyPageReturnDTO(banker.getName(), specializations,
                    banker.getContent(), banker.getFilePath(), totalGoodCount, totalCommentCount, totalViewCount);
        }

        return null;
    }
    // 행원의 총 도움돼요 수 구하기
    public Long calTotalGoodCount(Banker banker){
        if (banker == null || banker.getAnswers() == null) {
            return 0L;
        }

        return banker.getAnswers().stream()
                .mapToLong(Answer::getGoodCount)
                .sum();
    }
}
