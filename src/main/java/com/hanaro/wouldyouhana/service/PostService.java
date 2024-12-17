package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Category;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Post;
import com.hanaro.wouldyouhana.dto.comment.CommentDTO;
import com.hanaro.wouldyouhana.dto.post.PostAddRequestDTO;
import com.hanaro.wouldyouhana.dto.post.PostAllResponseDTO;
import com.hanaro.wouldyouhana.dto.post.PostResponseDTO;
import com.hanaro.wouldyouhana.repository.CategoryRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CustomerRepository customerRepository;

    // 커뮤니티 게시글 작성
    public int addPost(PostAddRequestDTO postAddRequestDTO, List<MultipartFile> files) {
        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findByName(postAddRequestDTO.getCategoryName());

        // 업로드한 파일에 대한 s3 버킷 내 주소들를 저장
        ArrayList<String> filePaths = new ArrayList<String>();

        if (files != null) {

            for (MultipartFile file : files) {

                String filePath = fileStorageService.saveFile(file); // S3 버킷 내 저장된 이미지의 링크 반환
                filePaths.add(filePath);

            }
        }

        Post newPost = Post.builder()
                .communityName(postAddRequestDTO.getCommunityName())
                .category(category)
                .title(postAddRequestDTO.getTitle())
                .customerId(postAddRequestDTO.getCustomerId())
                .content(postAddRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .filePaths(filePaths)
                .build();

        postRepository.save(newPost);
        return 1;
    }

    // 커뮤니티 게시글 수정
    public PostAllResponseDTO modifyPost(PostAddRequestDTO postAddRequestDTO, Long postId, List<MultipartFile> files) {
        // 기존 커뮤니티 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findByName(postAddRequestDTO.getCategoryName());

        List<String> filePaths = post.getFilePaths();

        // 새로운 이미지 파일 처리
        if (files != null) {
            for (MultipartFile file : files) {
                // 파일 시스템에 저장 로직 추가
                String filePath = fileStorageService.saveFile(file); // 파일 저장 후 경로를 반환하는 메서드
                filePaths.add(filePath);
            }
        }

        post.setTitle(postAddRequestDTO.getTitle());
        post.setContent(postAddRequestDTO.getContent());
        post.setCategory(category);
        post.setUpdatedAt(LocalDateTime.now());
        post.setFilePaths(filePaths);

        Post updatedPost = postRepository.save(post);

        return new PostAllResponseDTO(
                updatedPost.getId(),
                updatedPost.getCustomerId(),
                updatedPost.getCategory().getName(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getCommunityName(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt(),
                updatedPost.getLikeCount(),
                updatedPost.getScrapCount(),
                updatedPost.getViewCount(),
                updatedPost.getFilePaths()
        );

    }

    // 커뮤니티 게시글 삭제
    public void deletePost(Long postId) { postRepository.deleteById(postId); }

    // 커뮤니티 게시글 상세
    public PostResponseDTO getOnePost(Long postId) {
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Customer customer = customerRepository.findById(foundPost.getCustomerId()).get();

        List<CommentDTO> commentDTOS = foundPost.getComments().stream().map(comment ->
        {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCustomerId(comment.getCustomer().getId());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            return commentDTO;
        }).collect(Collectors.toList());

        foundPost.incrementViewCount();

        return new PostResponseDTO(
                foundPost.getId(),
                customer.getNickname(),
                foundPost.getCommunityName(),
                foundPost.getCategory().getName(),
                foundPost.getTitle(),
                foundPost.getContent(),
                foundPost.getCreatedAt(),
                foundPost.getUpdatedAt(),
                foundPost.getLikeCount(),
                foundPost.getScrapCount(),
                foundPost.getViewCount(),
                commentDTOS
        );
    }

}
