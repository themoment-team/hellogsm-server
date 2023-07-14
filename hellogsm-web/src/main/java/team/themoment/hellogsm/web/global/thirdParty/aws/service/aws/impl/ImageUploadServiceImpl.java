package team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.impl;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.ImageUploadService;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.exception.AwsTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {
    private final S3Template s3Template;
    private final AwsTemplate<String> executeWithExHandle;

    @Value("${spring.cloud.aws.s3.bucket-name}")
    String bucketName;

    @Override
    public String execute(MultipartFile multipartFile) {
        String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        return executeWithExHandle.execute(() -> {
            S3Resource resource = s3Template.upload(bucketName, createFileName(fileExtension), multipartFile.getInputStream());
            return resource.getURL().toString();
        });
    }

    private static String createFileName(String fileExtension) {
        return UUID.randomUUID().toString() + LocalDateTime.now() + "." + fileExtension;
    }

}
