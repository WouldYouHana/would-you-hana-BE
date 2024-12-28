package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Category;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Post;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.comment.CommentDTO;
import com.hanaro.wouldyouhana.dto.post.PostAddRequestDTO;
import com.hanaro.wouldyouhana.dto.post.PostAllResponseDTO;
import com.hanaro.wouldyouhana.dto.post.PostListDTO;
import com.hanaro.wouldyouhana.dto.post.PostResponseDTO;
import com.hanaro.wouldyouhana.dto.question.QnaListDTO;
import com.hanaro.wouldyouhana.repository.CategoryRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.PostRepository;
import com.hanaro.wouldyouhana.repository.ImageRepository;
import com.hanaro.wouldyouhana.domain.Image;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;
    private final CustomerRepository customerRepository;
    private final ImageRepository imageRepository;

    // 커뮤니티 게시글 작성
    public int addPost(PostAddRequestDTO postAddRequestDTO, List<MultipartFile> files) {
        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findByName(postAddRequestDTO.getCategoryName());

        Post newPost = Post.builder()
                .location(postAddRequestDTO.getLocation())
                .category(category)
                .title(postAddRequestDTO.getTitle())
                .customerId(postAddRequestDTO.getCustomerId())
                .content(postAddRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(newPost);

        // 파일 처리
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    String fileUrl = fileStorageService.saveFile(file);
                    Image image = Image.builder()
                            .filePath(fileUrl)
                            .post(savedPost)
                            .build();
                    imageRepository.save(image);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to upload file to S3: " + file.getOriginalFilename(), e);
                }
            }
        }
        return 1;
    }

    // 커뮤니티 게시글 수정
    public Long modifyPost(PostAddRequestDTO postAddRequestDTO, Long postId, List<MultipartFile> files) {
        // 기존 커뮤니티 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findByName(postAddRequestDTO.getCategoryName());

        // 기존 이미지 삭제
        imageRepository.deleteAllByPost_Id(postId);

        // 새로운 이미지 파일 처리
        if (files != null) {
            for (MultipartFile file : files) {
                String fileUrl = fileStorageService.saveFile(file);
                Image image = Image.builder()
                        .filePath(fileUrl)
                        .post(post)
                        .build();
                imageRepository.save(image);
            }
        }

        post.setTitle(postAddRequestDTO.getTitle());
        post.setContent(postAddRequestDTO.getContent());
        post.setCategory(category);
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post).getId();
    }

    // 커뮤니티 게시글 삭제
    public void deletePost(Long postId) { postRepository.deleteById(postId); }

    // 커뮤니티 게시글 상세
    public PostResponseDTO getOnePost(Long postId) {
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Customer customer = customerRepository.findById(foundPost.getCustomerId()).get();

        List<String> filePaths = imageRepository.findByPost_Id(postId)
                .stream()
                .map(Image::getFilePath)
                .collect(Collectors.toList());

        List<CommentDTO> commentDTOS = foundPost.getComments().stream().map(comment ->
        {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCustomerId(comment.getCustomer().getId());
            commentDTO.setNickname(comment.getCustomer().getNickname());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            return commentDTO;
        }).collect(Collectors.toList());

        foundPost.incrementViewCount();

        return new PostResponseDTO(
                foundPost.getId(),
                customer.getId(),
                customer.getNickname(),
                foundPost.getLocation(),
                foundPost.getCategory().getName(),
                foundPost.getTitle(),
                foundPost.getContent(),
                foundPost.getCreatedAt(),
                foundPost.getUpdatedAt(),
                foundPost.getLikeCount(),
                foundPost.getScrapCount(),
                foundPost.getViewCount(),
                filePaths,
                commentDTOS
        );
    }

    // 질문 글 목록 DTO(QnaListDTO) 만드는 공통 메서드
    // 질문 전체 목록, 카테고리별 질문 전체 목록, 고객별 질문 전체 목록에서 사용
    public List<PostListDTO> makePostListDTO(List<Post> fql) {
        List<PostListDTO> foundPostListDTO = fql.stream().map(post -> {
            Customer customer = customerRepository.findById(post.getCustomerId()).get();

            // 이미지 URL 리스트 조회
            List<String> filePaths = imageRepository.findByPost_Id(post.getId())
                    .stream()
                    .map(Image::getFilePath)
                    .collect(Collectors.toList());

            PostListDTO postListDTO = new PostListDTO();
            postListDTO.setPostId(post.getId());
            postListDTO.setNickname(customer.getNickname());
            postListDTO.setCategoryName(post.getCategory().getName());
            postListDTO.setTitle(post.getTitle());
            postListDTO.setContent(post.getContent());
            postListDTO.setLocation(post.getLocation());
            postListDTO.setCreatedAt(post.getCreatedAt());
            postListDTO.setCommentCount(post.getComments().size());
            postListDTO.setLikeCount(post.getLikeCount());
            postListDTO.setScrapCount(post.getScrapCount());
            postListDTO.setViewCount(post.getViewCount());
            postListDTO.setFilePaths(filePaths);

            return postListDTO;
        }).toList();
        return foundPostListDTO;
    }

    // 지역별 전체 게시글 조회
    public List<PostListDTO> getAllPosts(String location) {
        List<Post> foundQuestionList = postRepository.findByLocationOrderByCreatedAtDesc(location);
        return makePostListDTO(foundQuestionList);
    }

    // 카테고리별 게시글 조회
    public List<PostListDTO> getAllPostsByCategory(String categoryName, String location) {
        Category category = categoryRepository.findByName(categoryName);
        List<Post> foundPostList = postRepository.findByLocationAndCategoryId(location, category.getId());
        return makePostListDTO(foundPostList);
    }

    public List<PostListDTO> get3PostSortedByLatest(String location) {
        List<Post> foundPostList;
        // location이 비어 있으면 모든 게시글을 조회수(viewCount) 기준 내림차순으로 정렬
        if(location.isEmpty()){
            foundPostList = postRepository.findAll(Sort.by(Sort.Order.desc("viewCount")));
        } else {
            foundPostList = postRepository.findByLocationOrderByViewCountDesc(location);
        }

        // 조회수가 높은 상위 3개만 선택
        List<Post> top3Posts = foundPostList.size() > 3 ? foundPostList.subList(0, 3) : foundPostList;

        return makePostListDTO(top3Posts);
    }



}
