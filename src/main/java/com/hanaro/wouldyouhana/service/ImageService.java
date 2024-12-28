package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Customer;
import com.hanaro.wouldyouhana.domain.Image;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.ImageResponseDTO;
import com.hanaro.wouldyouhana.repository.CustomerRepository;
import com.hanaro.wouldyouhana.repository.ImageRepository;
import com.hanaro.wouldyouhana.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final String uploadDir = "path/to/your/upload/directory"; // 실제 경로로 변경

    private final ImageRepository imageRepository;
    private final QuestionRepository questionRepository;
    private final CustomerRepository customerRepository;

    public List<ImageResponseDTO> saveImages(List<MultipartFile> files, Long questionId) throws IOException {
        List<ImageResponseDTO> responseDTOs = new ArrayList<>();

        // Question 객체 조회
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        Customer customer = customerRepository.findById(question.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new IOException("File is empty");
            }

            // 파일 경로 생성
            String filePath = uploadDir + File.separator + file.getOriginalFilename();

            // 파일 저장
            File dest = new File(filePath);
            file.transferTo(dest);

            // 이미지 엔티티 생성
            Image image = Image.builder()
                    .filePath(filePath)
                    .question(question) // 조회한 Question 객체 사용
                    .customer(customer) // 조회한 Customer 객체 사용
                    .build();

            // 이미지 저장
            Image savedImage = imageRepository.save(image);

            // 응답 DTO 생성
            ImageResponseDTO responseDTO = new ImageResponseDTO(savedImage.getId(), savedImage.getFilePath());
            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }
}
