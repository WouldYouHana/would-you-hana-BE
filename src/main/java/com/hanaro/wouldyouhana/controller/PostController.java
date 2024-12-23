package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.comment.CommentAddRequestDTO;
import com.hanaro.wouldyouhana.dto.comment.CommentResponseDTO;
import com.hanaro.wouldyouhana.dto.post.PostAddRequestDTO;
import com.hanaro.wouldyouhana.dto.post.PostListDTO;
import com.hanaro.wouldyouhana.dto.post.PostResponseDTO;
import com.hanaro.wouldyouhana.service.CommentService;
import com.hanaro.wouldyouhana.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

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
    public ResponseEntity<Void> modifyPost(@PathVariable("postId") Long postId,
                                   @RequestPart("post") PostAddRequestDTO postAddRequestDTO,
                                   @RequestPart(value="file", required = false) List<MultipartFile> files) {
        postId = postService.modifyPost(postAddRequestDTO, postId, files);
        if(postId == null){ // 실패시
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else { // 성공시
            return new ResponseEntity<>(HttpStatus.OK);
        }

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

    // 게시글 찾기 실패시 예외 처리
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        // 에러 메시지를 반환하거나 커스터마이징하여 응답
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 커뮤니티 게시글 전체 조회

    @GetMapping("/postList")
    public ResponseEntity<List<PostListDTO>> getAllPosts(@RequestParam String location) {
        List<PostListDTO> postList = postService.getAllPosts(location);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    // 카테고리 별 게시글 전체 조회
    @GetMapping("/postList/{categoryId}")
    public ResponseEntity<List<PostListDTO>> getAllPostsByCategory(@PathVariable("categoryId") Long categoryId, @RequestParam String location) {
        List<PostListDTO> postByCategoryList = postService.getAllPostsByCategory(categoryId, location);
        return new ResponseEntity<>(postByCategoryList, HttpStatus.OK);
    }

    // 댓글 달기
    @PostMapping("/comment/{postId}")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable("postId") Long postId, @RequestBody CommentAddRequestDTO commentAddRequestDTO) {
        CommentResponseDTO addedComment = commentService.addCommentForPost(postId, commentAddRequestDTO);
        return new ResponseEntity<>(addedComment, HttpStatus.OK);

    }

    // 댓글 삭제
    @DeleteMapping("/comment/delete/{postId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteCommentForPost(postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
