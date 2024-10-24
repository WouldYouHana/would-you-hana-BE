package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.*;
import com.hanaro.wouldyouhana.repository.CommentRepository;
import com.hanaro.wouldyouhana.service.ImageService;
import com.hanaro.wouldyouhana.service.QnaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class QnaController {

    private final QnaService qnaService;
    private final ImageService imageService;
    private final CommentRepository commentRepository;

    @Autowired
    public QnaController(QnaService qnaService, ImageService imageService, CommentRepository commentRepository) {
        this.qnaService = qnaService;
        this.imageService = imageService;
        this.commentRepository = commentRepository;
    }

    /**
     * 질문(게시글) 등록
     * */
    @PostMapping("/posts")
    public ResponseEntity<QuestionAllResponseDTO> addNewQuestion(@Valid @RequestBody QuestionAddRequestDTO questionAddRequestDTO) {
        log.info("executed");
        QuestionAllResponseDTO createdPost = qnaService.addQuestion(questionAddRequestDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * 질문(게시글) 수정
     * */
    @PostMapping("/post/modify/{question_id}")
    public ResponseEntity<QuestionAllResponseDTO> modifyQuestion(@PathVariable Long question_id,
                                                                 @RequestBody QuestionAddRequestDTO questionAddRequestDTO){

        QuestionAllResponseDTO modifiedPost = qnaService.modifyQuestion(questionAddRequest, question_id);
        return new ResponseEntity<>(modifiedPost, HttpStatus.CREATED);
    }


    /**
     * 질문(게시글) 사진 등록
     * */
    @PostMapping("/posts/image/{question_id}")
    public ResponseEntity<List<ImageResponseDTO>> uploadImage(
            @PathVariable Long question_id,
            @RequestParam("file") List<MultipartFile> file) throws IOException {

        List<ImageResponseDTO> savedImg = imageService.saveImages(file, question_id);

        return new ResponseEntity<>(savedImg, HttpStatus.CREATED);
    }


    // 댓글 추가
    @PostMapping("/posts/{question_id}/comment")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long question_id,
                                                         @RequestBody CommentAddRequestDTO commentAddRequestDTO) {
        CommentResponseDTO addedComment = qnaService.addComment(question_id, commentAddRequestDTO);
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }

    // 게시글에 대한 댓글 가져오기


    // 댓글 삭제
    @DeleteMapping("/posts/{question_id}/{comment_id}")
    public ResponseEntity deleteComment(@PathVariable Long question_id, @PathVariable Long comment_id) {
        qnaService.deleteComment(question_id, comment_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 게시물 전체 조회
    @GetMapping("/qnalist")
    public ResponseEntity<List<QnaListDTO>> getAllQuestions() {
        List<QnaListDTO> questionList = qnaService.getAllQuestions();
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 카테고리별 게시물 전체 조회
    @GetMapping("/qnalist/{category_id}")
    public ResponseEntity<List<QnaListDTO>> getAllQuestionsByCategory(@PathVariable Long category_id) {
        List<QnaListDTO> questionByCategoryList = qnaService.getAllQuestionsByCategory(category_id);
        return new ResponseEntity<>(questionByCategoryList, HttpStatus.OK);
    }

    // 게시물 상세 조회
    @GetMapping("/posts/{question_id}")
    public ResponseEntity<QuestionResponseDTO> getOneQuestion(@PathVariable Long question_id) {
        QuestionResponseDTO questionResponseDTO = qnaService.getOneQuestion(question_id);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
    }
}
