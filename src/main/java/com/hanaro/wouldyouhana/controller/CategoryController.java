package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.CategoryDTO;
import com.hanaro.wouldyouhana.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public List<CategoryDTO> getAllCategory(){
        return categoryService.getAllCategory();
    }
}
