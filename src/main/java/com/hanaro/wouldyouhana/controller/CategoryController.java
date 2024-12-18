package com.hanaro.wouldyouhana.controller;

import com.hanaro.wouldyouhana.dto.CategoryDTO;
import com.hanaro.wouldyouhana.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public List<CategoryDTO> getAllCategory(){
        return categoryService.getAllCategory();
    }
}
