package com.hanaro.wouldyouhana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAddRequestDTO {

    @NotNull
    private String title;
    @NotNull
    private Long customer_id;
    @NotNull
    private Long category_id;
    @NotNull
    private String location;
    @NotNull
    private String content;

    private List<MultipartFile> file;

}
