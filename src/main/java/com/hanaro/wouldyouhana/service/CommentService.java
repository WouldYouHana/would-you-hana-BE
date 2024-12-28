package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Comment;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Post;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.comment.CommentAddRequestDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentResponseDTO;
import com.hanaro.wouldyouhana.dto.customer.CustomerResponseDTO;
import com.hanaro.wouldyouhana.repository.CommentRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.PostRepository;
import com.hanaro.wouldyouhana.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;

    // qna에 댓글 추가
    public CommentResponseDTO addCommentForQuestion(Long questionId, String userEmail, CommentAddRequestDTO commentAddRequestDTO) {
        Question foundQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer foundCustomer = customerRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Comment comment = Comment.builder()
                .question(foundQuestion)
                .customer(foundCustomer)
                .content(commentAddRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        foundQuestion.addComments(comment);
        Comment addedComment = commentRepository.save(comment);

        return new CommentResponseDTO(
                addedComment.getCustomer().getNickname(),
                addedComment.getQuestion().getId(),
                addedComment.getContent(),
                addedComment.getCreatedAt()
        );
    }

    // 커뮤니티 포스트에 댓글 추가
    public CommentResponseDTO addCommentForPost(Long postId, CommentAddRequestDTO commentAddRequestDTO) {
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer foundCustomer = customerRepository.findById(commentAddRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Comment comment = Comment.builder()
                .post(foundPost)
                .customer(foundCustomer)
                .content(commentAddRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        foundPost.addComments(comment);
        Comment addedComment = commentRepository.save(comment);

        return new CommentResponseDTO(
                addedComment.getCustomer().getNickname(),
                addedComment.getPost().getId(),
                addedComment.getContent(),
                addedComment.getCreatedAt()
        );
    }

    // 댓글 수정
//    public CommentResponseDTO updateComment(Long commentId, CommentAddRequestDTO commentAddRequestDTO) {
//        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("comment not found"));
//
//        foundComment.setContent(commentAddRequestDTO.getContent());
//
//    }

    // 게시글에 대한 댓글 가져오기
    public List<Comment> getCommentsByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("question not found"));
        return commentRepository.findByQuestion(question);
    }

    // 댓글 삭제
    public void deleteCommentForQuestion(Long questionId, Long commentId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        question.removeComments(comment);
        commentRepository.delete(comment);
    }

    // 댓글 삭제
    public void deleteCommentForPost(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        post.removeComments(comment);
        commentRepository.delete(comment);
    }

    // 지역구별 오늘 날짜 기준 댓글 수 많은 유저 3명 반환
    public List<CustomerResponseDTO> getTop3Customer(String location){
        // 오늘 날짜 기준 시작 시간 (00:00:00)
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();

        // 댓글을 작성한 유저의 댓글 수를 지역구별로 집계
        List<Object[]> topCustomersQuestion = commentRepository.findTop3CommentingUsersQuestion(location, startOfDay);
        List<Object[]> topCustomersPost = commentRepository.findTop3CommentingUsersPost(location, startOfDay);

        // 두 결과 리스트를 합친 후, 유저별 댓글 수를 합산
        Map<Long, Long> customerCommentCountMap = new HashMap<>();

        // 질문에 달린 댓글 합산
        for (Object[] result : topCustomersQuestion) {
            Long customerId = (Long) result[0];
            Long commentCount = (Long) result[1];
            customerCommentCountMap.put(customerId, customerCommentCountMap.getOrDefault(customerId, 0L) + commentCount);
        }

        // 게시물에 달린 댓글 합산
        for (Object[] result : topCustomersPost) {
            Long customerId = (Long) result[0];
            Long commentCount = (Long) result[1];
            customerCommentCountMap.put(customerId, customerCommentCountMap.getOrDefault(customerId, 0L) + commentCount);
        }

        // 유저 ID와 댓글 수를 기준으로 내림차순 정렬하여 상위 3명 선택
        List<Map.Entry<Long, Long>> sortedCustomers = customerCommentCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .limit(3) // 상위 3명만 선택
                .collect(Collectors.toList());

        // CustomerResponseDTO에 데이터 설정
        return sortedCustomers.stream()
                .map(entry -> {
                    Long customerId = entry.getKey();
                    Long commentCount = entry.getValue();

                    // Customer 정보를 가져오기
                    Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

                    // CustomerResponseDTO에 데이터 설정
                    CustomerResponseDTO dto = new CustomerResponseDTO();
                    dto.setNickname(customer.getNickname());
                    dto.setExperiencePoints(customer.getExperiencePoints());
                    dto.setTodayCommentCount(commentCount);  // 오늘 댓글 수
                    dto.setFilepath(customer.getFilepath());  // 프로필 이미지 경로 설정

                    // 추가적으로 Q&A와 게시물 수 카운트
                    long count = 0;
                    count += questionRepository.countByCustomerIdAndLocationAndCreatedAt(customer.getId(), location, startOfDay);
                    count += postRepository.countByCustomerIdAndLocationAndCreatedAt(customer.getId(), location, startOfDay);
                    dto.setQnaPostCount(count);

                    return dto;
                })
                .collect(Collectors.toList());
    }



}
