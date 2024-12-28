package com.hanaro.wouldyouhana.service;


import com.hanaro.wouldyouhana.domain.*;
import com.hanaro.wouldyouhana.dto.likesScrap.LikesRequestDTO;
import com.hanaro.wouldyouhana.dto.likesScrap.LikesResponseDTO;
import com.hanaro.wouldyouhana.dto.likesScrap.LikesScrapResponseDTO;
import com.hanaro.wouldyouhana.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@Transactional
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final CustomerRepository customerRepository;
    private final PostRepository postRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository, CustomerRepository customerRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.customerRepository = customerRepository;
        this.postRepository = postRepository;
    }


    /**
     * 좋아요 저장 - 커뮤니티 포스트 한정
     * */
    public Long saveLikes(LikesRequestDTO likesRequestDTO){
        Post post = postRepository.findById(likesRequestDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));
        Customer customer = customerRepository.findById(likesRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // 좋아요가 이미 클릭되어 있는지 확인
        boolean alreadyExists = likesRepository.existsByPostAndCustomer(post, customer);

        if(!alreadyExists){ // 좋아요

            // Likes 객체 생성 및 저장
            Likes likes = new Likes();
            likes.setPost(post);
            likes.setCustomer(customer);

            likesRepository.save(likes);
            // 게시글의 좋아요 개수 증가
            post.incrementLikesCount();

        }else{ //좋아요 취소

            // 해당 Likes 객체 찾기
            Likes likes = likesRepository.findByPostAndCustomer(post, customer)
                    .orElseThrow(() -> new EntityNotFoundException("Like not found for this question and customer."));

            // 커뮤니티 게시글의 좋아요 개수 감소
            post.decrementLikesCount();

            // Likes 객체 삭제
            likesRepository.delete(likes);

        }
        return post.getLikeCount();
    }

    /**
     * 좋아요 조회 (최신순)
     * */
    public List<LikesResponseDTO> getLikes(Long customer_id){

        Customer customer = customerRepository.findById(customer_id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // 고객의 좋아요 누른 포스트 조회
        List<Likes> likes = likesRepository.findByCustomer(customer);

        // Likes 객체를 LikesResponseDTO로 변환해서 최신순으로 정렬하여 반환
        return likes.stream()
                .sorted(Comparator.comparing(like -> like.getPost().getId(), Comparator.reverseOrder())) // ID 기준으로 내림차순 정렬
                .map(like -> {
                    String customerNickname = customerRepository.getNicknameById(like.getPost().getCustomerId());
                    return new LikesResponseDTO(like.getId(), like.getPost().getId(),like.getPost().getCategory().getName(),
                            customerNickname, like.getPost().getTitle(), like.getPost().getLikeCount(), like.getPost().getViewCount(),
                            Integer.toUnsignedLong(like.getPost().getComments().size()), like.getPost().getCreatedAt());
                })
                .toList(); // 변환된 DTO 리스트 반환
    }
}
