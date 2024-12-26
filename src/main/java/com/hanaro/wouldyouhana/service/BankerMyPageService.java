package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Answer;
import com.hanaro.wouldyouhana.domain.Banker;
import com.hanaro.wouldyouhana.domain.Specialization;
import com.hanaro.wouldyouhana.dto.banker.BankerMyPageEditInfoDTO;
import com.hanaro.wouldyouhana.dto.banker.BankerMyPageReturnDTO;
import com.hanaro.wouldyouhana.dto.banker.BankerProfileModifyDTO;
import com.hanaro.wouldyouhana.dto.myPage.BankerInfoResponseDTO;
import com.hanaro.wouldyouhana.dto.myPage.BankerInfoUpdateDTO;
import com.hanaro.wouldyouhana.repository.BankerRepository;
import com.hanaro.wouldyouhana.repository.BankerSpecializationRepository;
import com.hanaro.wouldyouhana.repository.SpecializationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankerMyPageService {

    private final BankerRepository bankerRepository;
    private final FileStorageService fileStorageService;
    private final BankerSpecializationRepository bankerSpecializationRepository;
    private final SpecializationRepository specializationRepository;

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

    public String modifyBankerProfile(BankerProfileModifyDTO bankerProfileModifyDTO, MultipartFile file) {
        Optional<Banker> optionalBanker = bankerRepository.findById(bankerProfileModifyDTO.getBankerId());
        if(optionalBanker.isPresent()) {
            Banker banker = optionalBanker.get();

            banker.setContent(bankerProfileModifyDTO.getContent());
            List<Specialization> s12nList = new ArrayList<>();
            for(String s12n : bankerProfileModifyDTO.getSpecializations()){
                Optional<Specialization> foundS12n = specializationRepository.findByName(s12n);
                if(foundS12n.isPresent()) {
                    Specialization presentS12n = foundS12n.get();
                    s12nList.add(presentS12n);
                }
            }
            banker.setSpecializations(s12nList);

            if(file != null){
                String filePath = fileStorageService.saveFile(file); // S3 버킷 내 저장된 이미지의 링크 반환
                banker.setFilePath(filePath);
            }

            bankerRepository.save(banker);
            return "Banker Profile Modified Successfully";

        }
        return "Banker Profile Not Found";
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

    // 행원의 개인정보 수정 전 필드에 데이터 채우기
    public BankerInfoResponseDTO getInfoBeforeUpdateInfo(Long bankerId){
        Banker banker = bankerRepository.findById(bankerId)
                .orElseThrow(() -> new EntityNotFoundException("Banker not found"));

        BankerInfoResponseDTO info = BankerInfoResponseDTO.builder()
                .bankerName(banker.getName())
                .bankerEmail(banker.getEmail())
                .branchName(banker.getBranchName())
                .build();

        return info;
    }

    public String updateBankerInfo(BankerInfoUpdateDTO bankerInfoUpdateDTO, Long bankerId){
        Banker banker = bankerRepository.findById(bankerId)
                .orElseThrow(()->new EntityNotFoundException("Banker not found"));

        banker.setPassword(bankerInfoUpdateDTO.getPassword());
        banker.setBranchName(bankerInfoUpdateDTO.getBranchName());

        bankerRepository.save(banker);
        return "Banker updated successfully";
    }
}
