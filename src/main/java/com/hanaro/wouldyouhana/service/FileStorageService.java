package com.hanaro.wouldyouhana.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3Client amazonS3Client;

    public FileStorageService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;

    }

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
        //Path filePath = Paths.get(uploadDir, uniqueFileName);

        try {
            // 파일을 저장합니다.
            //file.transferTo(filePath.toFile());
            return uploadFile(uniqueFileName, file);
        } catch (IOException e) {
            // 예외 처리: 파일 저장 실패 시 적절한 예외 처리
            throw new RuntimeException("Failed to store file " + originalFileName, e);
        }

        // 저장된 파일의 경로 반환
        //return filePath.toString();
    }

    public String uploadFile(String fileName, MultipartFile uploadFile) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(uploadFile.getSize());
        metadata.setContentType(uploadFile.getContentType());
        try {
            // 파일 업로드 관련 정보 출력
            System.out.println("버킷 이름: " + bucketName);
            System.out.println("파일 이름: " + fileName);
            System.out.println("파일 크기: " + uploadFile.getSize() + " bytes");
            System.out.println("파일 콘텐츠 타입: " + uploadFile.getContentType());

            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, fileName, uploadFile.getInputStream(), metadata) // PublicRead 권한으로 upload
            );

            // S3 URL 반환 (파일 URL은 AWS S3에서 객체 접근을 위한 URL)
            return amazonS3Client.getUrl(bucketName, fileName).toString();
        } catch (AmazonS3Exception e) {
            System.err.println("S3 업로드 실패: " + e.getMessage());
            throw new IOException("파일 업로드 중 오류 발생");
        }
    }

}
