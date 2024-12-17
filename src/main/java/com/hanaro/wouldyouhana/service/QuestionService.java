package com.hanaro.wouldyouhana.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.hanaro.wouldyouhana.domain.*;
import com.hanaro.wouldyouhana.dto.answer.AnswerResponseDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentDTO;
import com.hanaro.wouldyouhana.dto.question.*;
import com.hanaro.wouldyouhana.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final FileStorageService fileStorageService;
    private final LikesRepository likesRepository;
    private final AmazonS3Client amazonS3Client;
    private final AnswerRepository answerRepository;

//    @Autowired
//    public QuestionService(QuestionRepository questionRepository, CustomerRepository customerRepository, CategoryRepository categoryRepository, CommentRepository commentRepository,
//                           ImageRepository imageRepository, FileStorageService fileStorageService, LikesRepository likesRepository, AmazonS3Client amazonS3Client) {
//        this.questionRepository = questionRepository;
//        this.customerRepository = customerRepository;
//        this.categoryRepository = categoryRepository;
//        this.commentRepository = commentRepository;
//        this.imageRepository = imageRepository;
//        this.fileStorageService = fileStorageService;
//        this.likesRepository = likesRepository;
//        this.amazonS3Client = amazonS3Client;
//    }

    /**
     * 질문(게시글) 등록
     * */
    public QuestionAllResponseDTO addQuestion(QuestionAddRequestDTO questionAddRequestDTO, List<MultipartFile> files) {

        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findByName(questionAddRequestDTO.getCategoryName());

        Customer customer = customerRepository.findById(questionAddRequestDTO.getCustomerId()).get();
        // 업로드한 파일에 대한 s3 버킷 내 주소들를 저장
        ArrayList<String> filePaths = new ArrayList<String>();

        if (files != null) {

            for (MultipartFile file : files) {

                String filePath = fileStorageService.saveFile(file); // S3 버킷 내 저장된 이미지의 링크 반환
                filePaths.add(filePath);

//                Image image = Image.builder()
//                        .filePath(filePath) // 파일 경로 설정
//                        .question(savedQuestion) // 질문과 연결
//                        .build();
//                // 이미지 저장
//                imageRepository.save(image);
            }
        }
        // 질문 엔티티 생성
        Question question = Question.builder()
                .customerId(questionAddRequestDTO.getCustomerId())
                .category(category)
                .title(questionAddRequestDTO.getTitle())
                .content(questionAddRequestDTO.getContent())
                .location(questionAddRequestDTO.getLocation())
                .createdAt(LocalDateTime.now())
                .filePaths(filePaths)
                .build();

        // 질문 저장
        Question savedQuestion = questionRepository.save(question);

        // 최종적으로 반환할 DTO 생성
        return new QuestionAllResponseDTO(
                savedQuestion.getId(),
                customer.getNickname(),
                savedQuestion.getCategory().getName(),
                savedQuestion.getTitle(),
                savedQuestion.getContent(),
                savedQuestion.getLocation(),
                savedQuestion.getCreatedAt(),
                savedQuestion.getUpdatedAt(), // 추가: 업데이트 시간
                Integer.toUnsignedLong(0), // likeCount (초기값)
                Integer.toUnsignedLong(0), // scrapCount (초기값)
                Integer.toUnsignedLong(0), // viewCount (초기값)
                filePaths
        );
    }

    /**
     * 질문(게시글) 수정
     * */
    public Long modifyQuestion(QuestionAddRequestDTO questionAddRequestDTO, Long questionId, List<MultipartFile> files) {
        // 기존 질문 엔티티 조회
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findByName(questionAddRequestDTO.getCategoryName());
        Customer customer = customerRepository.findById(questionAddRequestDTO.getCustomerId()).get();
        // 기존 이미지 삭제
        //imageRepository.deleteAllByQuestionId(questionId);

        List<String> filePaths = question.getFilePaths();

        // 새로운 이미지 파일 처리
        if (files != null) {
            for (MultipartFile file : files) {
                // 파일 시스템에 저장 로직 추가
                String filePath = fileStorageService.saveFile(file); // 파일 저장 후 경로를 반환하는 메서드
                filePaths.add(filePath);
//                Image image = Image.builder()
//                        .filePath(filePath) // 파일 경로 설정
//                        .question(updatedQuestion) // 질문과 연결
//                        .build();
//                // 이미지 저장
//                imageRepository.save(image);
            }
        }
        // 질문 정보 수정
        question.setTitle(questionAddRequestDTO.getTitle());
        question.setContent(questionAddRequestDTO.getContent());
        question.setLocation(questionAddRequestDTO.getLocation());
        question.setCategory(category);
        question.setUpdatedAt(LocalDateTime.now());
        question.setFilePaths(filePaths);

        // 질문 저장
        Question updatedQuestion = questionRepository.save(question);

        return questionId;
    }

    // 질문 삭제
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    // 질문 상세
    public QuestionResponseDTO getOneQuestion(Long questionId) {
        Question foundQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        Customer customer = customerRepository.findById(foundQuestion.getCustomerId()).get();
        AnswerResponseDTO answerResponseDTO = answerRepository.findByQuestionId(questionId)
                .map(answer -> new AnswerResponseDTO(
                        answer.getBanker().getName(),
                        foundQuestion.getId(),
                        foundQuestion.getContent(),
                        foundQuestion.getCreatedAt(),
                        foundQuestion.getUpdatedAt()
                ))
                .orElse(null);  // 답변이 없으면 null을 반환

        List<CommentDTO> commentDTOS = foundQuestion.getComments().stream().map(comment ->
        {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCustomerId(comment.getCustomer().getId());
            commentDTO.setCreatedAt(LocalDateTime.now());
            return commentDTO;
        }).collect(Collectors.toList());

        foundQuestion.incrementViewCount();

        return new QuestionResponseDTO(
                foundQuestion.getId(),
                customer.getNickname(),
                foundQuestion.getCategory().getName(),
                foundQuestion.getTitle(),
                foundQuestion.getContent(),
                foundQuestion.getLocation(),
                foundQuestion.getCreatedAt(),
                foundQuestion.getUpdatedAt(),
                foundQuestion.getLikeCount(),
                foundQuestion.getScrapCount(),
                foundQuestion.getViewCount(),
                answerResponseDTO,
                commentDTOS
        );
    }

    // 오늘의 인기 질문
    public List<TodayQnaListDTO> getTodayQuestions(String location) {
        // 오늘 날짜의 시작 시간과 끝 시간을 계산
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();  // 오늘 00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59);  // 오늘 23:59:59

        // 오늘 날짜의 질문들 중에서 viewCount가 높은 6개 가져오기
        List<Question> foundQuestionList = questionRepository.findTop6ByCreatedAtTodayOrderByViewCountDesc(location, startOfDay, endOfDay, PageRequest.of(0, 6));

        // Question -> TodayQnaListDTO로 변환
        return foundQuestionList.stream()
                .map(question -> new TodayQnaListDTO(
                        question.getId(),
                        question.getTitle(),
                        question.getViewCount()))
                .collect(Collectors.toList());
    }


    // 질문 글 목록 DTO(QnaListDTO) 만드는 공통 메서드
    // 질문 전체 목록, 카테고리별 질문 전체 목록, 고객별 질문 전체 목록에서 사용
    public List<QnaListDTO> makeQnaListDTO(List<Question> fql) {

        List<QnaListDTO> foundQuestionListDTO = fql.stream().map(question -> {

            String answerBanker = Optional.ofNullable(question.getAnswers())
                    .map(answers -> answers.getBanker())
                    .map(banker -> banker.getName())
                    .orElse(null);

            QnaListDTO qnaListDTO = new QnaListDTO();
            qnaListDTO.setQuestionId(question.getId());
            qnaListDTO.setCustomerId(question.getCustomerId());
            qnaListDTO.setAnswerBanker(answerBanker); //이거
            qnaListDTO.setCategoryName(question.getCategory().getName()); //이것도
            qnaListDTO.setTitle((question.getTitle()));
            qnaListDTO.setLocation(question.getLocation());
            qnaListDTO.setCreatedAt(question.getCreatedAt());
            qnaListDTO.setCommentCount(Integer.toUnsignedLong(question.getComments().size()));
            qnaListDTO.setLikeCount(question.getLikeCount());
            qnaListDTO.setScrapCount(question.getScrapCount());
            qnaListDTO.setViewCount(question.getViewCount());

            return qnaListDTO;
        }).collect(Collectors.toList());
        return foundQuestionListDTO;
    }

    // 지역별 전체 게시글 조회
    public List<QnaListDTO> getAllQuestions(String location) {
        List<Question> foundQuestionList = questionRepository.findByLocation(location);
        return makeQnaListDTO(foundQuestionList);
    }

    // 지역별 최신순 게시글 조회
    public List<QnaListDTO> getAllQuestionsSortedByLatest(String location) {
        List<Question> foundQuestionList = questionRepository.findByLocationOrderByCreatedAtDesc(location);
        return makeQnaListDTO(foundQuestionList);
    }

    // 지역별 좋아요 순 게시글 조회
    public List<QnaListDTO> getAllQuestionsSortedByLikes(String location) {
        List<Question> foundQuestionList = questionRepository.findByLocationOrderByLikeCountDesc(location);
        return makeQnaListDTO(foundQuestionList);
    }

    // 카테고리별 질문 전체 목록
    public List<QnaListDTO> getAllQuestionsByCategory(String categoryName, String location) {
        Category category = categoryRepository.findByName(categoryName);
        List<Question> foundQuestionList = questionRepository.findByLocationAndCategory_Id(location, category.getId());
        return makeQnaListDTO(foundQuestionList);
    }

    // 고객별 질문 전체 목록
    public List<QnaListDTO> getAllQuestionsByCustomerId(Long customerId) {
        List<Question> foundQuestionList = questionRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new EntityNotFoundException("No Question for this customer"));
        return makeQnaListDTO(foundQuestionList);
    }


}
