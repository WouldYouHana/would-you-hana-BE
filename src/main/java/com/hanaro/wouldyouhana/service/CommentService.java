package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Comment;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.comment.CommentAddRequestDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentResponseDTO;
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
        Question foundQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer foundCustomer = customerRepository.findById(commentAddRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Comment pComment = null;
        // 부모 댓글 id가 있으면
        if (parentCommentId != null) {
            pComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new EntityNotFoundException("No parent comment found"));
        }

        Comment comment = Comment.builder()
                .question(foundQuestion)
                .customer(foundCustomer)
                .content(commentAddRequestDTO.getContent())
                .parentComment(pComment)
                .createdAt(LocalDateTime.now())
                .build();

        foundQuestion.addComments(comment);
        Comment addedComment = commentRepository.save(comment);

        return new CommentResponseDTO(
                addedComment.getCustomer().getId(),
                addedComment.getQuestion().getId(),
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
    public void deleteComment(Long questionId, Long commentId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        question.removeComments(comment);
        commentRepository.delete(comment);
    }

}
