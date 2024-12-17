package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.post.PostAddRequestDTO;
import com.hanaro.wouldyouhana.dto.question.QuestionAddRequestDTO;
import com.hanaro.wouldyouhana.dto.question.QuestionAllResponseDTO;
import com.hanaro.wouldyouhana.service.PostService;
import com.hanaro.wouldyouhana.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;


    @PostMapping("/qna/register")
    public ResponseEntity<Void> addNewPost(@Valid
                                                                 @RequestPart("question") PostAddRequestDTO postAddRequestDTO,
                                                                 @RequestPart(value = "file", required = false) List<MultipartFile> files) throws IOException {
        int result = postService.addPost(postAddRequestDTO, files);
        if(result == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
