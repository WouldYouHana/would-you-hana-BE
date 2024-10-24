package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.*;
import com.hanaro.wouldyouhana.dto.*;
import com.hanaro.wouldyouhana.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class QnaService {
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public QnaService(QuestionRepository questionRepository, CustomerRepository customerRepository, CategoryRepository categoryRepository, CommentRepository commentRepository,
                      ImageRepository imageRepository, FileStorageService fileStorageService) {
        this.questionRepository = questionRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.imageRepository = imageRepository;
        this.fileStorageService = fileStorageService;
    }

    /**
     * 질문(게시글) 등록
     * */
    public QuestionAllResponseDTO addQuestion(QuestionAddRequestDTO questionAddRequestDTO) {

        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findById(questionAddRequestDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다.")); // 예외 처리 추가

        // 질문 엔티티 생성
        Question question = Question.builder()
                .customer_id(questionAddRequestDTO.getCustomer_id())
                .category(category)
                .title(questionAddRequestDTO.getTitle())
                .content(questionAddRequestDTO.getContent())
                .location(questionAddRequestDTO.getLocation())
                .created_at(LocalDateTime.now())
                .build();

        // 질문 저장
        Question savedQuestion = questionRepository.save(question);

        if (questionAddRequestDTO.getFile() != null) {
            for (MultipartFile file : questionAddRequestDTO.getFile()) {
                // 파일 시스템에 저장 로직 추가
                String filePath = fileStorageService.saveFile(file); // 파일 저장 후 경로를 반환하는 메서드
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
                savedQuestion.getId(),
                savedQuestion.getCustomer_id(),
                savedQuestion.getCategory().getId(),
                savedQuestion.getTitle(),
                savedQuestion.getContent(),
                savedQuestion.getLocation(),
                savedQuestion.getCreated_at(),
                savedQuestion.getUpdated_at(), // 추가: 업데이트 시간
                0, // likeCount (초기값)
                0, // scrapCount (초기값)
                0, // viewCount (초기값)
                questionAddRequestDTO.getFile().stream().map(MultipartFile::getOriginalFilename).toList()
        );
    }


    /**
     * 질문(게시글) 수정
     * */
    public QuestionAllResponseDTO modifyQuestion(QuestionAddRequestDTO questionAddRequestDTO, Long question_id) {
        // 기존 질문 엔티티 조회
        Question question = questionRepository.findById(question_id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        // 질문 정보 수정
        question.setTitle(questionAddRequestDTO.getTitle());
        question.setContent(questionAddRequestDTO.getContent());
        question.setLocation(questionAddRequestDTO.getLocation());
        question.setUpdated_at(LocalDateTime.now()); // 수정 시간 업데이트

        // 질문 저장
        Question updatedQuestion = questionRepository.save(question);

        // 기존 이미지 삭제
        imageRepository.deleteAllByQuestionId(question_id);

        // 새로운 이미지 파일 처리
        if (questionAddRequestDTO.getFile() != null) {
            for (MultipartFile file : questionAddRequestDTO.getFile()) {
                // 파일 시스템에 저장 로직 추가
                String filePath = fileStorageService.saveFile(file); // 파일 저장 후 경로를 반환하는 메서드
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
                updatedQuestion.getId(),
                updatedQuestion.getCustomer_id(),
                updatedQuestion.getCategory().getId(),
                updatedQuestion.getTitle(),
                updatedQuestion.getContent(),
                updatedQuestion.getLocation(),
                updatedQuestion.getCreated_at(),
                updatedQuestion.getUpdated_at(), // 업데이트 시간
                updatedQuestion.getLikeCount(), // likeCount (초기값)
                updatedQuestion.getScrapCount(), // scrapCount (초기값)
                updatedQuestion.getViewCount(), // viewCount (초기값)
                questionAddRequestDTO.getFile().stream().map(MultipartFile::getOriginalFilename).toList() // 파일 이름 리스트 추가
        );
    }

    // 댓글 추가
    public CommentResponseDTO addComment(Long questionId, CommentAddRequestDTO commentAddRequestDTO) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer customer = customerRepository.findById(commentAddRequestDTO.getCustomer_id())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Comment comment = Comment.builder()
                .question(question)
                .customer(customer)
                .content(commentAddRequestDTO.getContent())
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

    // 질문 글 목록 DTO(QnaListDTO) 만드는 공통 메서드
    // 질문 전체 목록, 카테고리별 질문 전체 목록에서 사용
    public List<QnaListDTO> makeQnaListDTO(List<Question> fql) {
        List<QnaListDTO> foundQuestionListDTO = fql.stream().map(question -> {
            QnaListDTO qnaListDTO = new QnaListDTO();
            qnaListDTO.setQuestion_id(question.getId());
            qnaListDTO.setCustomer_id(question.getCustomer_id());
            qnaListDTO.setCategory_id(question.getCategory().getId());
            qnaListDTO.setTitle((question.getTitle()));
            qnaListDTO.setContent(question.getContent());
            qnaListDTO.setLocation(question.getLocation());
            qnaListDTO.setCreated_at(question.getCreated_at());
            qnaListDTO.setCommentCount(Long.valueOf(question.getComments().size()));
            qnaListDTO.setLikeCount(Long.valueOf(question.getLikeCount()));

            return qnaListDTO;
        }).collect(Collectors.toList());
        return foundQuestionListDTO;
    }

    // 질문 전체 목록
    public List<QnaListDTO> getAllQuestions() {
        List<Question> foundQuestionList = questionRepository.findAll();
        return makeQnaListDTO(foundQuestionList);
    }

    // 카테고리별 질문 전체 목록
    public List<QnaListDTO> getAllQuestionsByCategory(Long categoryId) {
        List<Question> foundQuestionList = questionRepository.findByCategory_id(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("No Question for category"));
        return makeQnaListDTO(foundQuestionList);
    }

    // 질문 상세
    public QuestionResponseDTO getOneQuestion(Long questionId) {
        Question foundQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        List<CommentDTO> commentDTOS = foundQuestion.getComments().stream().map(comment ->
        {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCustomer_id(comment.getCustomer().getId());
            commentDTO.setCreated_at(LocalDateTime.now());
            return commentDTO;
        }).collect(Collectors.toList());

        return new QuestionResponseDTO(
                foundQuestion.getId(),
                foundQuestion.getCustomer_id(),
                foundQuestion.getCategory().getId(),
                foundQuestion.getTitle(),
                foundQuestion.getContent(),
                foundQuestion.getLocation(),
                foundQuestion.getCreated_at(),
                commentDTOS
        );
    }

}
