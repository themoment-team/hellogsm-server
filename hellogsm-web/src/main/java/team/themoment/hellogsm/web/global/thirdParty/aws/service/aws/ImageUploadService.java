package team.themoment.hellogsm.web.global.thirdParty.aws.service.aws;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String execute(MultipartFile multipartFile);
}
