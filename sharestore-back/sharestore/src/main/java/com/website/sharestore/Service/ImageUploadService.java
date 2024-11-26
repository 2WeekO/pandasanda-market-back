package com.website.sharestore.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile image) {
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        try {
            // S3에 이미지 업로드
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, image.getInputStream(), null));
            // 이미지 URL 생성
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public void deleteImage(String imageName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, imageName));
    }
}