package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Category;
import com.hanaro.wouldyouhana.domain.Post;
import com.hanaro.wouldyouhana.dto.post.PostAddRequestDTO;
import com.hanaro.wouldyouhana.repository.CategoryRepository;
import com.hanaro.wouldyouhana.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public int addPost(PostAddRequestDTO postAddRequestDTO, List<MultipartFile> files) {
        // 카테고리 ID로 카테고리 객체 가져오기
        Category category = categoryRepository.findByName(postAddRequestDTO.getCategoryName());

//        Post newPost = Post.builder()
//                .
        return 1;
    }
}
