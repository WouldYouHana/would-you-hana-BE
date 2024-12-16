package com.hanaro.wouldyouhana.util;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class S3Uploader {
    private final AmazonS3Client s3Client;
    //private final String bucketName;
}
