package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.*;
import com.hanaro.wouldyouhana.dto.answer.AnswerAddRequestDTO;
import com.hanaro.wouldyouhana.dto.answer.AnswerResponseDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentAddRequestDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentResponseDTO;
import com.hanaro.wouldyouhana.dto.question.*;
import com.hanaro.wouldyouhana.forSignIn.SecurityUtil;
import com.hanaro.wouldyouhana.repository.AnswerRepository;
import com.hanaro.wouldyouhana.service.AnswerService;
import com.hanaro.wouldyouhana.service.CommentService;
import com.hanaro.wouldyouhana.service.ImageService;
import com.hanaro.wouldyouhana.service.QuestionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class QnaController {

    private final QuestionService questionService;
    private final ImageService imageService;
    private final CommentService commentService;
    private final AnswerService answerService;
    private final AnswerRepository answerRepository;

    @Autowired
    public QnaController(QuestionService questionService, ImageService imageService, CommentService commentService, AnswerService answerService, AnswerRepository answerRepository) {
        this.questionService = questionService;
        this.imageService = imageService;
        this.commentService = commentService;
        this.answerService = answerService;
        this.answerRepository = answerRepository;
    }

    /**
     * 질문(게시글) 등록
     * */
    @PostMapping("/post")
    public ResponseEntity<QuestionAllResponseDTO> addNewQuestion(@Valid @RequestBody QuestionAddRequestDTO questionAddRequestDTO) {
        log.info("executed");
        QuestionAllResponseDTO createdPost = questionService.addQuestion(questionAddRequestDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * 질문(게시글) 수정
     * */

    @PostMapping("/post/modify/{questionId}")
    public ResponseEntity<QuestionAllResponseDTO> modifyQuestion(@PathVariable Long questionId,
                                                                 @RequestBody QuestionAddRequestDTO questionAddRequestDTO){

        QuestionAllResponseDTO modifiedPost = questionService.modifyQuestion(questionAddRequestDTO, questionId);
        return new ResponseEntity<>(modifiedPost, HttpStatus.CREATED);
    }

    /**
     * 질문(게시글) 삭제
     * */

    @PreAuthorize("@customerRepository.findByEmail(principal.getUsername()).getId() == @questionRepository.findById(#question_id).customerId")
    @DeleteMapping("/post/delete/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 질문(게시글) 사진 등록
     * */
    @PostMapping("/post/image/{questionId}")
    public ResponseEntity<List<ImageResponseDTO>> uploadImage(
            @PathVariable Long questionId,
            @RequestParam("file") List<MultipartFile> file) throws IOException {

        List<ImageResponseDTO> savedImg = imageService.saveImages(file, questionId);

        return new ResponseEntity<>(savedImg, HttpStatus.CREATED);
    }

    // 답변 등록 - 행원
    @PostMapping("/post/answer/{questionId}")
    public ResponseEntity<AnswerResponseDTO> addAnswer(@PathVariable Long questionId,
                                                       @RequestHeader("Authorization") String authorizationHeader,
                                                       @RequestBody AnswerAddRequestDTO answerAddRequestDTO) {
        String userEmail = SecurityUtil.getCurrentUsername();
        AnswerResponseDTO addedAnswer = answerService.addAnswer(questionId, answerAddRequestDTO);
        return new ResponseEntity<>(addedAnswer, HttpStatus.CREATED);
    }

//    @DeleteMapping("/post/answer/{questionId}/{answerId}")
//    public ResponseEntity deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
//        questionService.
//        answerService.deleteAnswer(answerId);
//    }

    // 댓글 추가
    @PostMapping("/post/comment/{questionId}")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long questionId,
                                                         @RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestBody CommentAddRequestDTO commentAddRequestDTO) {
        String userEmail = SecurityUtil.getCurrentUsername();
        CommentResponseDTO addedComment = commentService.addComment(questionId, null, userEmail, commentAddRequestDTO);
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }

    // 대댓글 추가
//    @PostMapping("/post/replycomment/{questionId}/{parentCommentId}")
//    public ResponseEntity<CommentResponseDTO> createReply(@PathVariable Long questionId, @PathVariable Long parentCommentId, @RequestBody CommentAddRequestDTO commentAddRequestDTO) {
//        CommentResponseDTO addedComment = commentService.addComment(questionId, parentCommentId, commentAddRequestDTO);
//        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
//    }

    // 게시글에 대한 댓글 가져오기


    // 댓글 삭제
    @DeleteMapping("/post/comment/{questionId}/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long questionId, @PathVariable Long commentId) {
        commentService.deleteComment(questionId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 오늘의 인기 게시글
     * */
    @GetMapping("/todatQnalist")
    public ResponseEntity<List<TodayQnaListDTO>> getTodayQuestions() {
        List<TodayQnaListDTO> questionList = questionService.getTodayQuestions();
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }


    // 게시물 전체 조회
    @GetMapping("/qnalist")
    public ResponseEntity<List<QnaListDTO>> getAllQuestions() {
        List<QnaListDTO> questionList = questionService.getAllQuestions();
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 카테고리별 게시물 전체 조회
    @GetMapping("/qnalist/{categoryId}")
    public ResponseEntity<List<QnaListDTO>> getAllQuestionsByCategory(@PathVariable Long categoryId) {
        List<QnaListDTO> questionByCategoryList = questionService.getAllQuestionsByCategory(categoryId);
        return new ResponseEntity<>(questionByCategoryList, HttpStatus.OK);
    }

    // 고객별 게시물 전체 조회
    @GetMapping("/mypage/questions/{customerId}")
    public ResponseEntity<List<QnaListDTO>> getAllQuestionsByCustomer(@PathVariable Long customerId) {
        List<QnaListDTO> questionByCustomerList = questionService.getAllQuestionsByCustomerId(customerId);
        return new ResponseEntity<>(questionByCustomerList, HttpStatus.OK);
    }

    // 게시물 상세 조회
    @GetMapping("/post/{questionId}")
    public ResponseEntity<QuestionResponseDTO> getOneQuestion(@PathVariable Long questionId) {
        QuestionResponseDTO questionResponseDTO = questionService.getOneQuestion(questionId);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
    }
}
