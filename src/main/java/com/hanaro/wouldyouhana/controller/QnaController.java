package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.*;
import com.hanaro.wouldyouhana.dto.answer.AnswerAddRequestDTO;
import com.hanaro.wouldyouhana.dto.answer.AnswerGoodRequestDTO;
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
@RequestMapping("/qna")
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
    @PostMapping("/register")
    public ResponseEntity<QuestionAllResponseDTO> addNewQuestion(@Valid
                                                                     @RequestPart("question") QuestionAddRequestDTO questionAddRequestDTO,
                                                                 @RequestPart(value = "file", required = false) List<MultipartFile> files) throws IOException {
        log.info("executed");
        QuestionAllResponseDTO createdPost = questionService.addQuestion(questionAddRequestDTO, files);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * 질문(게시글) 수정
     * */
    @PutMapping("/modify/{questionId}")
    public ResponseEntity<Void> modifyQuestion(@PathVariable Long questionId,
                                                                 @RequestPart("question") QuestionAddRequestDTO questionAddRequestDTO,
                                                                    @RequestPart(value = "file", required = false) List<MultipartFile> files){
        questionId = questionService.modifyQuestion(questionAddRequestDTO, questionId, files);
        if(questionId == null){ // 실패시
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else { // 성공시
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * 질문(게시글) 삭제
     * */
    @PreAuthorize("@customerRepository.findByEmail(principal.getUsername()).getId() == @questionRepository.findById(#question_id).customerId")
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 질문(게시글) 사진 등록
     * */
//    @PostMapping("/qna/image/{questionId}")
//    public ResponseEntity<List<ImageResponseDTO>> uploadImage(
//            @PathVariable Long questionId,
//            @RequestParam("file") List<MultipartFile> file) throws IOException {
//
//        List<ImageResponseDTO> savedImg = imageService.saveImages(file, questionId);
//
//        return new ResponseEntity<>(savedImg, HttpStatus.CREATED);
//    }

    // 답변 등록 - 행원
    @PostMapping("/answer/{questionId}")
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
    @PostMapping("/comment/{question_id}")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long question_id,
                                                         @RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestBody CommentAddRequestDTO commentAddRequestDTO) {
        String userEmail = SecurityUtil.getCurrentUsername();
        CommentResponseDTO addedComment = commentService.addComment(question_id, userEmail, commentAddRequestDTO);
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }

    // 대댓글 추가
//    @PostMapping("/post/replycomment/{questionId}/{parentCommentId}")
//    public ResponseEntity<CommentResponseDTO> createReply(@PathVariable Long questionId, @PathVariable Long parentCommentId, @RequestBody CommentAddRequestDTO commentAddRequestDTO) {
//        CommentResponseDTO addedComment = commentService.addComment(questionId, parentCommentId, commentAddRequestDTO);
//        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
//    }

    // 댓글 삭제
    @DeleteMapping("/comment/{questionId}/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long questionId, @PathVariable Long commentId) {
        commentService.deleteComment(questionId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 오늘의 인기 게시글 - 좋아요 (TOP6)
     * */
    @GetMapping("/todayQnaList")
    public ResponseEntity<List<TodayQnaListDTO>> getTodayQuestions(@RequestParam String location) {
        List<TodayQnaListDTO> questionList = questionService.getTodayQuestions(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 게시물 전체 조회 (순서 없음)
    @GetMapping("/qnaList")
    public ResponseEntity<List<QnaListDTO>> getAllQuestions(@RequestParam String location) {
        List<QnaListDTO> questionList = questionService.getAllQuestions(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 지역별 게시물 전체 조회 (최신순)
    @GetMapping("/qnaList/latest")
    public ResponseEntity<List<QnaListDTO>> getLatestQuestions(@RequestParam String location) {
        List<QnaListDTO> questionList = questionService.getAllQuestionsSortedByLatest(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 지역별 게시물 전체 조회 (좋아요순)
    @GetMapping("/qnaList/likes")
    public ResponseEntity<List<QnaListDTO>> getLikesQuestions(@RequestParam String location) {
        List<QnaListDTO> questionList = questionService.getAllQuestionsSortedByLikes(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 지역별 게시물 전체 조회 (답변 도움 순)
    @GetMapping("/qnaList/good")
    public ResponseEntity<List<QnaListDTO>> getGoodQuestions(@RequestParam String location) {
        List<QnaListDTO> questionList = questionService.getAllQuestionsSortedByGoodCount(location);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 카테고리별 게시물 전체 조회
    @GetMapping("/qnaList/{category}")
    public ResponseEntity<List<QnaListDTO>> getAllQuestionsByCategory(@PathVariable String category, @RequestParam String location) {
        List<QnaListDTO> questionByCategoryList = questionService.getAllQuestionsByCategory(category, location);
        return new ResponseEntity<>(questionByCategoryList, HttpStatus.OK);
    }

    // 고객별 게시물 전체 조회
    @GetMapping("/mypage/questions/{customerId}")
    public ResponseEntity<List<QnaListDTO>> getAllQuestionsByCustomer(@PathVariable Long customerId) {
        List<QnaListDTO> questionByCustomerList = questionService.getAllQuestionsByCustomerId(customerId);
        return new ResponseEntity<>(questionByCustomerList, HttpStatus.OK);
    }

    // 게시물 상세 조회
    @GetMapping("/{question_id}")
    public ResponseEntity<QuestionResponseDTO> getOneQuestion(@PathVariable Long question_id) {
        QuestionResponseDTO questionResponseDTO = questionService.getOneQuestion(question_id);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
    }

    // 답변 도움돼요 등록
    @PostMapping("/answerLike")
    public ResponseEntity<String> goodRequest(@RequestBody AnswerGoodRequestDTO answerGoodRequestDTO){
        questionService.saveGood(answerGoodRequestDTO);
        return ResponseEntity.ok("answerLike Success");
    }
}
