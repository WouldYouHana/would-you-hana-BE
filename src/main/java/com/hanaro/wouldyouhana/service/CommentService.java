package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Comment;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Post;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.comment.CommentAddRequestDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentResponseDTO;
import com.hanaro.wouldyouhana.repository.CommentRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.PostRepository;
import com.hanaro.wouldyouhana.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CommentService(CustomerRepository customerRepository, QuestionRepository questionRepository,
                          PostRepository postRepository, CommentRepository commentRepository) {
        this.customerRepository = customerRepository;
        this.questionRepository = questionRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

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
                addedComment.getCustomer().getName(),
                addedComment.getQuestion().getId(),
                addedComment.getContent(),
                addedComment.getCreatedAt()
        );
    }

    // 커뮤니티 포스트에 댓글 추가
    public CommentResponseDTO addCommentForPost(Long postId, String userEmail, CommentAddRequestDTO commentAddRequestDTO) {
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer foundCustomer = customerRepository.findByEmail(userEmail)
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

}
