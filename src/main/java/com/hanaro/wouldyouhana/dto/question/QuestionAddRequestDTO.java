package com.hanaro.wouldyouhana.dto.question;

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
    private Long customerId;
    @NotNull
    private String categoryName;
    @NotNull
    private String location;
    @NotNull
    private String content;

}
