package team.themoment.hellogsm.web.global.thirdParty.aws.service.application.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.themoment.hellogsm.web.domain.application.service.ImageSaveService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;
import team.themoment.hellogsm.web.global.thirdParty.aws.service.aws.ImageUploadService;

import java.util.Objects;

@Service
@XRayEnabled
@RequiredArgsConstructor
public class ImageSaveServiceImpl implements ImageSaveService {
    private final ImageUploadService imageUploadService;

    @Override
    public String execute(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new ExpectedException("파일이 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        if (!Objects.requireNonNull(multipartFile.getContentType()).contains("image"))
            throw new ExpectedException("파일 형식이 올바르지 않습니다", HttpStatus.BAD_REQUEST);

        return imageUploadService.execute(multipartFile);
    }
}
