package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Category;
import com.hanaro.wouldyouhana.dto.CategoryDTO;
import com.hanaro.wouldyouhana.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();

        // Category를 CategoryDTO로 변환
        return categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

}
