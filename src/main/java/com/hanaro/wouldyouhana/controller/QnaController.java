package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.ImageResponseDTO;
import com.hanaro.wouldyouhana.dto.QuestionAddRequest;
import com.hanaro.wouldyouhana.dto.QuestionAllResponseDTO;
import com.hanaro.wouldyouhana.dto.QuestionResponseDTO;
import com.hanaro.wouldyouhana.service.ImageService;
import com.hanaro.wouldyouhana.service.QnaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class QnaController {

    private final QnaService qnaService;
    private final ImageService imageService;

    @Autowired
    public QnaController(QnaService qnaService, ImageService imageService) {
        this.qnaService = qnaService;
        this.imageService = imageService;
    }

    /**
     * 질문(게시글) 등록
     * */
    @PostMapping("/posts")
    public ResponseEntity<QuestionAllResponseDTO> addNewQuestion(@Valid @RequestBody QuestionAddRequest questionAddRequest) {
        log.info("executed");
        QuestionAllResponseDTO createdPost = qnaService.addQuestion(questionAddRequest);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * 질문(게시글) 수정
     * */
    @PostMapping("/post/modify/{question_id}")
    public ResponseEntity<QuestionAllResponseDTO> modifyQuestion(@PathVariable Long question_id,
                                                                 @RequestBody QuestionAddRequest questionAddRequest){

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


    // 게시글에 대한 댓글 가져오기


    // 댓글 삭제


    // 게시물 전체 조회
    @GetMapping("/qnalist")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questionList = qnaService.getAllQuestions();
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    // 카테고리별 게시물 전체 조회
    @GetMapping("/qnalist/{category_id}")
    public ResponseEntity<List<Question>> getAllQuestionsByCategory(@RequestParam Long category_id) {
        List<Question> questionByCategoryList = qnaService.getAllQuestionsByCategory(category_id);
        return new ResponseEntity<>(questionByCategoryList, HttpStatus.OK);
    }

    @GetMapping("/posts/{question_id}")
    public ResponseEntity<QuestionResponseDTO> getOneQuestion(@RequestParam Long question_id) {
        QuestionResponseDTO questionResponseDTO = qnaService.getOneQuestion(question_id);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
    }
}
