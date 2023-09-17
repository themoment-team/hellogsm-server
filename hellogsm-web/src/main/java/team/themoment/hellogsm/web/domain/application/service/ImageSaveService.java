package team.themoment.hellogsm.web.domain.application.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 이미지를 저장하는 service interface 입니다.
 */
public interface ImageSaveService {
    String execute(MultipartFile multipartFile);
}
