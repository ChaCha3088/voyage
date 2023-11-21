package com.ssafy.voyage.service;

import com.amazonaws.SdkBaseException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.ssafy.voyage.message.message.ImageMessages.*;
import static com.ssafy.voyage.message.message.Messages.*;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;
    private static final String SERVICE_NAME = "voyage";
    private static final String DOMAIN_NAME = "member";
    private static final String PROFILE_IMAGE = "profile_image";
    private static final String PROFILE_IMAGE_NAME = "profile_image";
    private static int MAXIMUM_UPLOAD_SIZE = 4194304; // 4MB
    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif");

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // S3에 이미지 업로드
    @Transactional(rollbackFor = IOException.class)
    public String uploadImageToS3(String email, MultipartFile multipartFile) throws IllegalArgumentException, SdkBaseException, IOException {
        // multipartFile이 null이면, 예외 발생
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException(new StringBuffer().append(MULTIPART_FILE.getMessage()).append(NOT_FOUND.getMessage()).toString());
        }

        // 파일 이름
        String fileName = Optional.ofNullable(multipartFile.getOriginalFilename())
            .orElseThrow(() -> new IllegalArgumentException(new StringBuffer().append(MULTIPART_FILE.getMessage()).append(NAME.getMessage()).append(INVALID.getMessage()).toString()));

        // 파일 확장자
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        // Content-Type
        String contentType = "";

        switch (fileType) {
            case "jpg":
            case "jpeg":
                contentType = "image/jpg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "gif":
                contentType = "image/gif";
                break;
        }

        // 파일 확장자 검증
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(fileType)) {
            throw new IllegalArgumentException(new StringBuffer().append(MULTIPART_FILE.getMessage()).append(EXTENSION.getMessage()).append(INVALID.getMessage()).toString());
        }

        // 이메일 형식 변환
        String replacedEmail = convertEmail(email);

        // S3에 업로드할 파일 경로
        String fileDir = SERVICE_NAME + "/" + DOMAIN_NAME + "/" + replacedEmail + "/" + PROFILE_IMAGE;

        // 크기 4MB 미만인지 검증
        if (multipartFile.getSize() >= MAXIMUM_UPLOAD_SIZE) {
            throw new IllegalArgumentException(new StringBuffer().append(MULTIPART_FILE.getMessage()).append(SIZE.getMessage()).append(INVALID.getMessage()).toString());
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);

        String wholeName = fileDir + "/" + PROFILE_IMAGE_NAME + "." + fileType;

        amazonS3.putObject(new PutObjectRequest(bucket, wholeName, multipartFile.getInputStream(), metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, wholeName).toString();
    }

    // S3에서 파일 삭제
    @Transactional
    public void deleteFromS3(String profileImageUrl) throws IllegalArgumentException, SdkBaseException {
        // .com/ 이후의 문자열을 key로 사용
        String key = profileImageUrl.substring(profileImageUrl.indexOf(".com/") + 5);

        amazonS3.deleteObject(bucket, key);
    }

    private static String convertEmail(String email) {
        // 이메일 형식 변환
        String replacedEmail = email.replace("@", "_");
        replacedEmail = replacedEmail.replace(".", "_");

        return replacedEmail;
    }
}
