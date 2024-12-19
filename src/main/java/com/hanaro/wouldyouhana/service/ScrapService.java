package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.*;
import com.hanaro.wouldyouhana.dto.likesScrap.*;
import com.hanaro.wouldyouhana.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ScrapService {

    @Autowired
    private ScrapQuestionRepository scrapQuestionRepository;
    @Autowired
    private ScrapPostRepository scrapPostRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PostRepository postRepository;

    /**
     * qna 스크랩 저장 & 취소
     * */
    public void saveScrapForQuestion(ScrapQuestionRequestDTO scrapQuestionRequestDTO) {
        Question question = questionRepository.findById(scrapQuestionRequestDTO.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Customer customer = customerRepository.findById(scrapQuestionRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        boolean alreadyExists = scrapQuestionRepository.existsByQuestionAndCustomer(question, customer);

        if(!alreadyExists){ // 스크랩

            // Scrap 객체 생성 및 저장
            ScrapQuestion scrapQuestion = new ScrapQuestion();
            scrapQuestion.setQuestion(question);
            scrapQuestion.setCustomer(customer);

            scrapQuestionRepository.save(scrapQuestion);

            // 게시물의 스크랩 수 증가
            question.incrementScrapCount();

        }else{ //스크랩 취소

            // 해당 Scrap 객체 찾기
            ScrapQuestion scrapQuestion = scrapQuestionRepository.findByQuestionAndCustomer(question, customer)
                    .orElseThrow(() -> new EntityNotFoundException("Like not found for this question and customer."));

            // 게시물의 스크랩 수 감소
            question.decrementScrapCount();

            // Scrap 객체 삭제
            scrapQuestionRepository.delete(scrapQuestion);
        }
    }
    /**
     * qna 스크랩 조회 (최신순)
     * */
    public List<ScrapQuestionResponseDTO> getScrapForQuestion(Long customer_id){

        Customer customer = customerRepository.findById(customer_id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // 고객의 스크랩 조회
        List<ScrapQuestion> scrapQuestions = scrapQuestionRepository.findByCustomer(customer);

        // Scrap 객체를 ScrapResponseDTO로 변환해서 최신순으로 정렬하여 반환
        return scrapQuestions.stream()
                .sorted(Comparator.comparing(scrapQuestion -> scrapQuestion.getQuestion().getId(), Comparator.reverseOrder())) // ID 기준으로 내림차순 정렬
                .map(scrapQuestion -> {
                    String customerNickname = customerRepository.getNicknameById(scrapQuestion.getQuestion().getCustomerId());
                    String bankerName = "답변 대기 중";
                    if(scrapQuestion.getQuestion().getAnswers() != null){
                        bankerName = scrapQuestion.getQuestion().getAnswers().getBanker().getName();
                    }
                    return new ScrapQuestionResponseDTO(scrapQuestion.getId(), scrapQuestion.getQuestion().getId(), scrapQuestion.getQuestion().getCategory().getName(),
                            scrapQuestion.getQuestion().getTitle(), customerNickname, scrapQuestion.getQuestion().getLikeCount(), scrapQuestion.getQuestion().getViewCount(),
                            Integer.toUnsignedLong(scrapQuestion.getQuestion().getComments().size()),scrapQuestion.getQuestion().getCreatedAt(), scrapQuestion.getQuestion().getUpdatedAt(), bankerName);
                })
                .toList(); // 변환된 DTO 리스트 반환
    }

    /**
     * 커뮤니티 포스트 스크랩 저장 & 취소
     * */
    public void saveScrapForPost(ScrapPostRequestDTO scrapPostRequestDTO) {
        Post post = postRepository.findById(scrapPostRequestDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Customer customer = customerRepository.findById(scrapPostRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        boolean alreadyExists = scrapPostRepository.existsByPostAndCustomer(post, customer);

        if(!alreadyExists){ // 스크랩

            // Scrap 객체 생성 및 저장
            ScrapPost scrapPost = new ScrapPost();
            scrapPost.setPost(post);
            scrapPost.setCustomer(customer);

            scrapPostRepository.save(scrapPost);

            // 게시물의 스크랩 수 증가
            post.incrementScrapCount();

        }else{ //스크랩 취소

            // 해당 Scrap 객체 찾기
            ScrapPost scrapPost = scrapPostRepository.findByPostAndCustomer(post, customer)
                    .orElseThrow(() -> new EntityNotFoundException("Like not found for this question and customer."));

            // 게시물의 스크랩 수 감소
            post.decrementScrapCount();

            // Scrap 객체 삭제
            scrapPostRepository.delete(scrapPost);
        }
    }

    /**
     * 커뮤니티 포스트 스크랩 조회 (최신순)
     * */
    public List<ScrapPostResponseDTO> getScrapForPost(Long customer_id){

        Customer customer = customerRepository.findById(customer_id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // 고객의 스크랩 조회
        List<ScrapPost> scrapPosts = scrapPostRepository.findByCustomer(customer);

        // Scrap 객체를 ScrapResponseDTO로 변환해서 최신순으로 정렬하여 반환
        return scrapPosts.stream()
                .sorted(Comparator.comparing(scrapPost -> scrapPost.getPost().getId(), Comparator.reverseOrder())) // ID 기준으로 내림차순 정렬
                .map(scrapPost -> {
                    String customerNickname = customerRepository.getNicknameById(scrapPost.getPost().getCustomerId());
                    return new ScrapPostResponseDTO(scrapPost.getId(), scrapPost.getPost().getId(), scrapPost.getPost().getCategory().getName(),
                            scrapPost.getPost().getTitle(), customerNickname, scrapPost.getPost().getLikeCount(), scrapPost.getPost().getViewCount(),
                            Integer.toUnsignedLong(scrapPost.getPost().getComments().size()),scrapPost.getPost().getCreatedAt(), scrapPost.getPost().getUpdatedAt());
                }).toList(); // 변환된 DTO 리스트 반환
    }
}
