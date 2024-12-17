package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.post.PostAddRequestDTO;
import com.hanaro.wouldyouhana.dto.post.PostAllResponseDTO;
import com.hanaro.wouldyouhana.dto.post.PostResponseDTO;
import com.hanaro.wouldyouhana.dto.question.QuestionAddRequestDTO;
import com.hanaro.wouldyouhana.dto.question.QuestionAllResponseDTO;
import com.hanaro.wouldyouhana.service.PostService;
import com.hanaro.wouldyouhana.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    // 커뮤니티 게시글 등록
    @PostMapping("/register")
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

    // 커뮤니티 게시글 수정
    @PutMapping("/modify/{postId}")
    public RedirectView modifyPost(@PathVariable("postId") Long postId,
                                   @RequestPart("post") PostAddRequestDTO postAddRequestDTO,
                                   @RequestPart(value="file", required = false) List<MultipartFile> files) {
        PostAllResponseDTO modifiedPost = postService.modifyPost(postAddRequestDTO, postId, files);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/post/" + modifiedPost.getPostId());  // 수정된 게시글의 ID를 사용하여 상세 페이지로 리다이렉트

        return redirectView;  // 리다이렉트 응답 반환
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 커뮤니티 게시글 상세
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable("postId") Long postId) {
        PostResponseDTO postResponseDTO = postService.getOnePost(postId);
        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    // 커뮤니티 게시글 전체 조회
//    @GetMapping("/postList")
//    public ResponseEntity<List<PostResponseDTO>> getPostList() {
//
//    }
}
