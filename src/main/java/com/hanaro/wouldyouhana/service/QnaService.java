package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Comment;
import com.hanaro.wouldyouhana.domain.Image;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.dto.ImageResponseDTO;
import com.hanaro.wouldyouhana.dto.QuestionAddRequest;
import com.hanaro.wouldyouhana.dto.QuestionAllResponseDTO;
import com.hanaro.wouldyouhana.dto.QuestionResponseDTO;
import com.hanaro.wouldyouhana.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class QnaService {
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public QnaService(QuestionRepository questionRepository, CustomerRepository customerRepository, CategoryRepository categoryRepository, CommentRepository commentRepository,
                      ImageRepository imageRepository) {
        this.questionRepository = questionRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * 질문(게시글) 등록
     * */
    public QuestionAllResponseDTO addQuestion(QuestionAddRequest questionAddRequest) {
        // 질문 엔티티 생성
        Question question = Question.builder()
                .customer_id(questionAddRequest.getCustomer_id())
                .category_id(questionAddRequest.getCategory_id())
                .title(questionAddRequest.getTitle())
                .content(questionAddRequest.getContent())
                .location(questionAddRequest.getLocation())
                .created_at(LocalDateTime.now())
                .build();

        // 질문 저장
        Question savedQuestion = questionRepository.save(question);

        // 이미지 파일 경로가 있을 경우
        if (questionAddRequest.getFile() != null) {
            for (String filePath : questionAddRequest.getFile()) {
                Image image = Image.builder()
                        .file_path(filePath) // 파일 경로 설정
                        .question(savedQuestion) // 질문과 연결
                        .build();
                // 이미지 저장
                imageRepository.save(image);
            }
        }

        // 최종적으로 반환할 DTO 생성
        return new QuestionAllResponseDTO(
                savedQuestion.getQuestion_id(),
                savedQuestion.getCustomer_id(),
                savedQuestion.getCategory_id(),
                savedQuestion.getTitle(),
                savedQuestion.getContent(),
                savedQuestion.getLocation(),
                savedQuestion.getCreated_at(),
                savedQuestion.getUpdated_at(), // 추가: 업데이트 시간
                0, // likeCount (초기값)
                0, // scrapCount (초기값)
                0, // viewCount (초기값)
                questionAddRequest.getFile() // 파일 경로 리스트 추가
        );
    }


    /**
     * 질문(게시글) 수정
     * */
    public QuestionAllResponseDTO modifyQuestion(QuestionAddRequest questionAddRequest, Long question_id){

        // 기존 질문 엔티티 조회
        Question question = questionRepository.findById(question_id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        // 질문 정보 수정
        question.setTitle(questionAddRequest.getTitle());
        question.setContent(questionAddRequest.getContent());
        question.setLocation(questionAddRequest.getLocation());
        question.setUpdated_at(LocalDateTime.now()); // 수정 시간 업데이트

        // 질문 저장
        Question updatedQuestion = questionRepository.save(question);

        // 기존 이미지 삭제 (선택적으로, 모든 이미지를 삭제하려는 경우)
        imageRepository.deleteAllByQuestionId(question_id);

        // 새로운 이미지 파일 경로가 있을 경우
        if (questionAddRequest.getFile() != null) {
            for (String filePath : questionAddRequest.getFile()) {
                Image image = Image.builder()
                        .file_path(filePath) // 파일 경로 설정
                        .question(updatedQuestion) // 질문과 연결
                        .build();
                // 이미지 저장
                imageRepository.save(image);
            }
        }

        // 최종적으로 반환할 DTO 생성
        return new QuestionAllResponseDTO(
                updatedQuestion.getQuestion_id(),
                updatedQuestion.getCustomer_id(),
                updatedQuestion.getCategory_id(),
                updatedQuestion.getTitle(),
                updatedQuestion.getContent(),
                updatedQuestion.getLocation(),
                updatedQuestion.getCreated_at(),
                updatedQuestion.getUpdated_at(), // 업데이트 시간
                updatedQuestion.getLikeCount(), // likeCount (초기값)
                updatedQuestion.getScrapCount(), // scrapCount (초기값)
                updatedQuestion.getViewCount(), // viewCount (초기값)
                questionAddRequest.getFile() // 파일 경로 리스트 추가
        );


    }


    // 댓글 추가
    public Comment addComment(Long questionId, Long customerId, String content) {
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
    public List<Comment> getCommentsByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("question not found"));
        return commentRepository.findByQuestion(question);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }

    // 질문 목록
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // 질문 상세
    public QuestionResponseDTO getOneQuestion(Long questionId) {
        Question foundQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return new QuestionResponseDTO(
                foundQuestion.getQuestion_id(),
                foundQuestion.getCustomer_id(),
                foundQuestion.getCategory_id(),
                foundQuestion.getTitle(),
                foundQuestion.getContent(),
                foundQuestion.getLocation(),
                foundQuestion.getCreated_at()
        );
    }

}
