package com.hanaro.wouldyouhana.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}") // application.properties에서 경로를 가져옵니다.
    private String uploadDir;

    public String saveFile(MultipartFile file) {
        // 파일 이름 가져오기
        String originalFileName = file.getOriginalFilename();

        // 파일 확장자 추출 (예: .jpg, .png)
        String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";

        // UUID를 사용하여 고유한 파일 이름 생성
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // 파일 저장 경로 생성
        Path filePath = Paths.get(uploadDir, uniqueFileName);

        try {
            // 파일을 저장합니다.
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            // 예외 처리: 파일 저장 실패 시 적절한 예외 처리
            throw new RuntimeException("Failed to store file " + originalFileName, e);
        }

        // 저장된 파일의 경로 반환
        return filePath.toString();
    }

}
