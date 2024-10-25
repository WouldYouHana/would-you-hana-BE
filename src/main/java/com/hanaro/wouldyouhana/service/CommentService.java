package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Comment;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.CommentAddRequestDTO;
import com.hanaro.wouldyouhana.dto.CommentResponseDTO;
import com.hanaro.wouldyouhana.repository.CommentRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;

    @Autowired
    public CommentService(CustomerRepository customerRepository, QuestionRepository questionRepository, CommentRepository commentRepository) {
        this.customerRepository = customerRepository;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
    }

    // 댓글 추가
    public CommentResponseDTO addComment(Long questionId, Long parentCommentId, CommentAddRequestDTO commentAddRequestDTO) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer customer = customerRepository.findById(commentAddRequestDTO.getCustomer_id())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Comment pComment = null;
        // 부모 댓글 id가 있으면
        if (parentCommentId != null) {
            pComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new EntityNotFoundException("No parent comment found"));
        }

        Comment comment = Comment.builder()
                .question(question)
                .customer(customer)
                .content(commentAddRequestDTO.getContent())
                .parentComment(pComment)
                .created_at(LocalDateTime.now())
                .build();

        question.addComments(comment);
        Comment addedComment = commentRepository.save(comment);

        return new CommentResponseDTO(
                addedComment.getCustomer().getId(),
                addedComment.getQuestion().getId(),
                addedComment.getContent(),
                addedComment.getCreated_at()
        );
    }

    // 게시글에 대한 댓글 가져오기
    public List<Comment> getCommentsByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("question not found"));
        return commentRepository.findByQuestion(question);
    }

    // 댓글 삭제
    public void deleteComment(Long questionId, Long commentId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        question.removeComments(comment);
        commentRepository.delete(comment);
    }

}
