package com.hanaro.wouldyouhana.service;


import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Likes;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.domain.Scrap;
import com.hanaro.wouldyouhana.dto.LikesScrapRequestDTO;
import com.hanaro.wouldyouhana.dto.LikesScrapResponseDTO;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.LikesRepository;
import com.hanaro.wouldyouhana.repository.QuestionRepository;
import com.hanaro.wouldyouhana.repository.ScrapRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@Transactional
public class LikesScrapService {

    private final ScrapRepository scrapRepository;
    private final LikesRepository likesRepository;
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public LikesScrapService(ScrapRepository scrapRepository, LikesRepository likesRepository, QuestionRepository questionRepository, CustomerRepository customerRepository) {
        this.scrapRepository = scrapRepository;
        this.likesRepository = likesRepository;
        this.questionRepository = questionRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * 스크랩 저장
     * */
    public void saveScrap(LikesScrapRequestDTO likesScrapRequestDTO) {
        Question question = questionRepository.findById(likesScrapRequestDTO.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Customer customer = customerRepository.findById(likesScrapRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));


        if(likesScrapRequestDTO.isSelected()){ // 스크랩

            // 이미 같은 question과 customer로 좋아요 존재하는지 확인 (중복 확인)
            boolean alreadyExists = scrapRepository.existsByQuestionAndCustomer(question, customer);
            if (alreadyExists) {
                throw new IllegalArgumentException("Already scraped this question by the customer.");
            }

            // Scrap 객체 생성 및 저장
            Scrap scrap = new Scrap();
            scrap.setQuestion(question);
            scrap.setCustomer(customer);

            scrapRepository.save(scrap);
        }else{ //스크랩 취소

            // 해당 Scrap 객체 찾기
            Scrap scrap = scrapRepository.findByQuestionAndCustomer(question, customer)
                    .orElseThrow(() -> new EntityNotFoundException("Like not found for this question and customer."));

            // Scrap 객체 삭제
            scrapRepository.delete(scrap);
        }
    }

    /**
     * 좋아요 저장
     * */
    public void saveLikes(LikesScrapRequestDTO likesScrapRequestDTO){
        Question question = questionRepository.findById(likesScrapRequestDTO.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Customer customer = customerRepository.findById(likesScrapRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        if(likesScrapRequestDTO.isSelected()){ // 좋아요

            // 이미 같은 question과 customer로 좋아요 존재하는지 확인 (중복 확인)
            boolean alreadyExists = likesRepository.existsByQuestionAndCustomer(question, customer);
            if (alreadyExists) {
                throw new IllegalArgumentException("Already scraped this question by the customer.");
            }

            // Likes 객체 생성 및 저장
            Likes likes = new Likes();
            likes.setQuestion(question);
            likes.setCustomer(customer);

            likesRepository.save(likes);
        }else{ //좋아요 취소

            // 해당 Likes 객체 찾기
            Likes likes = likesRepository.findByQuestionAndCustomer(question, customer)
                    .orElseThrow(() -> new EntityNotFoundException("Like not found for this question and customer."));

            // Likes 객체 삭제
            likesRepository.delete(likes);
        }
    }

    /**
     * 스크랩 조회 (최신순)
     * */
    public List<LikesScrapResponseDTO> getScrap(Long customer_id){

        Customer customer = customerRepository.findById(customer_id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // 고객의 스크랩 조회
        List<Scrap> scraps = scrapRepository.findByCustomer(customer);

        // Scrap 객체를 ScrapResponseDTO로 변환해서 최신순으로 정렬하여 반환
        return scraps.stream()
                .sorted(Comparator.comparing(scrap -> scrap.getQuestion().getId(), Comparator.reverseOrder())) // ID 기준으로 내림차순 정렬
                .map(scrap -> new LikesScrapResponseDTO(scrap.getId(), scrap.getQuestion().getId(),scrap.getQuestion().getCategory().getName(),
                        scrap.getQuestion().getTitle(), scrap.getQuestion().getLikeCount(), scrap.getQuestion().getViewCount(),
                        scrap.getQuestion().getCreated_at(), scrap.getQuestion().getAnswers().getBanker().getName()))
                .toList(); // 변환된 DTO 리스트 반환
    }

    /**
     * 좋아요 조회 (최신순)
     * */
    public List<LikesScrapResponseDTO> getLikes(Long customer_id){

        Customer customer = customerRepository.findById(customer_id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // 고객의 스크랩 조회
        List<Likes> likes = likesRepository.findByCustomer(customer);

        // Scrap 객체를 ScrapResponseDTO로 변환해서 최신순으로 정렬하여 반환
        return likes.stream()
                .sorted(Comparator.comparing(like -> like.getQuestion().getId(), Comparator.reverseOrder())) // ID 기준으로 내림차순 정렬
                .map(like -> new LikesScrapResponseDTO(like.getId(), like.getQuestion().getId(),like.getQuestion().getCategory().getName(),
                        like.getQuestion().getTitle(), like.getQuestion().getLikeCount(), like.getQuestion().getViewCount(),
                        like.getQuestion().getCreated_at(), like.getQuestion().getAnswers().getBanker().getName()))
                .toList(); // 변환된 DTO 리스트 반환
    }
}
