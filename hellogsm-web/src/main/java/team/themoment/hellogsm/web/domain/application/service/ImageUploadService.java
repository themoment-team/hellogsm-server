package team.themoment.hellogsm.web.domain.application.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String execute(MultipartFile multipartFile);
}
