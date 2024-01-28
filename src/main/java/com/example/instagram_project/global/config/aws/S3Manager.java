package com.example.instagram_project.global.config.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor
@Component
public class S3Manager {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

            // 업로드된 파일의 URL 생성
            URL fileUrl = amazonS3Client.getUrl(bucket, fileName);

            return fileUrl.toString();
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리: 실패한 경우 null을 반환하거나 적절한 방식으로 처리
            return null;
        }
    }

    public void deleteFile(String image) {
        amazonS3Client.deleteObject(bucket, image);
    }
}
