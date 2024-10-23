package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Comment;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.dto.QuestionAddRequest;
import com.hanaro.wouldyouhana.dto.QuestionResponseDTO;
import com.hanaro.wouldyouhana.repository.CategoryRepository;
import com.hanaro.wouldyouhana.repository.CommentRepository;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Transactional
public class QnaService {
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public QnaService(QuestionRepository questionRepository, CustomerRepository customerRepository, CategoryRepository categoryRepository, CommentRepository commentRepository) {
        this.questionRepository = questionRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 질문 등록
     * */
    public QuestionResponseDTO addQuestion(QuestionAddRequest questionAddRequest) {
        // 고객과 카테고리를 데이터베이스에서 조회
        Category category = categoryRepository.findById(questionAddRequest.getCategory_id())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
      
        Question question = Question.builder()
                .customer_id(questionAddRequest.getCustomer_id())
                .category_id(questionAddRequest.getCategory_id())
                .title(questionAddRequest.getTitle())
                .content(questionAddRequest.getContent())
                .location(questionAddRequest.getLocation())
                .created_at(LocalDateTime.now())
                .build();

        Question savedQuestion = questionRepository.save(question);

        return new QuestionResponseDTO(
                savedQuestion.getQuestion_id(),
                savedQuestion.getCustomer_id(),
                savedQuestion.getCategory().getCategory_id(),
                savedQuestion.getTitle(),
                savedQuestion.getContent(),
                savedQuestion.getLocation(),
                savedQuestion.getCreated_at()
        );
    }

    // 댓글 추가
    public Comment addComment(Integer questionId, Long customerId, String content) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Comment comment = Comment.builder()
                .question(question)
                .customer(customer)
                .content(content)
                .created_at(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    // 게시글에 대한 댓글 가져오기
    public List<Comment> getCommentsByQuestionId(Integer questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("question not found"));
        return commentRepository.findByQuestion(question);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }
}
