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

/**
 * 이미지를 저장하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class ImageSaveServiceImpl implements ImageSaveService {
    private final ImageUploadService imageUploadService;

    /**
     * 매개변수로 이미지 파일을 받아 저장 후 이미지 url을 반환해 줍니다.
     *
     * @param multipartFile 이미지 파일
     * @return 이미지 url
     * @throws ExpectedException 발생조건은 아래와 같음 <br>
     *      1. 파일이 존재하지 않을 경우 <br>
     *      2. 파일 형식이 올바르지 않을 경우 <br>
     */
    @Override
    public String execute(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new ExpectedException("파일이 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        if (!Objects.requireNonNull(multipartFile.getContentType()).contains("image"))
            throw new ExpectedException("파일 형식이 올바르지 않습니다", HttpStatus.BAD_REQUEST);

        return imageUploadService.execute(multipartFile);
    }
}
