package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.mapper.IdentityMapper;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * 현재 사용자의 원서를 수정하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class ModifyApplicationServiceImpl implements ModifyApplicationService {
    private final UserRepository userRepository;
    private final IdentityRepository identityRepository;
    private final ApplicationRepository applicationRepository;

    /**
     * 수정할 원서 정보와 본인인증 정보를 바탕으로 원서를 수정합니다.
     *
     * @param body 수정할 원서 정보
     * @param userId 요청한 유저의 pk값
     * @throws ExpectedException 발생조건은 아래와 같음 <br>
     *      1. 존재하지 않는 유저일 경우 <br>
     *      2. 원서가 존재하지 않을 경우 <br>
     *      3. 최종제출이 완료된 원서일 경우 <br>
     *      4. 본인인증 정보가 존재하지 않을 경우 <br>
     */
    @Override
    public void execute(ApplicationReqDto body, Long userId, boolean isAdmin) {
        if (!userRepository.existsById(userId))
            throw new ExpectedException("존재하지 않는 유저입니다", HttpStatus.BAD_REQUEST);

        Application application = applicationRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("원서가 존재하지 않습니다", HttpStatus.BAD_REQUEST));
        if (!isAdmin && application.getAdmissionStatus().isFinalSubmitted())
            throw new ExpectedException("최종제출이 완료된 원서입니다", HttpStatus.BAD_REQUEST);

        if(!identityRepository.existsByUserId(userId)) {
            throw new ExpectedException("Identity가 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        }

        applicationRepository.save(
                ApplicationMapper.INSTANCE.updateApplicationByApplicationReqDtoAndApplication(body, application));
    }
}
