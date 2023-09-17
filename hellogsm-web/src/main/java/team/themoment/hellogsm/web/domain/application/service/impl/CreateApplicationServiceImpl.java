package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.CreateApplicationService;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.mapper.IdentityMapper;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.user.repository.UserRepository;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * 원서를 생성하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class CreateApplicationServiceImpl implements CreateApplicationService {
    private final UserRepository userRepository;
    private final IdentityRepository identityRepository;
    private final ApplicationRepository applicationRepository;

    /**
     * 본인인증 정보와 원서에 작성할 정보를 바탕으로 원서를 생성합니다.
     *
     * @param body 원서에 작성할 정보
     * @param userId 요청한 유저의 pk값
     * @throws ExpectedException 발생조건은 아래와 같음 <br>
     *      1. 존재하지 않는 유저일 경우 <br>
     *      2. 원서가 이미 존재할 경우 <br>
     *      3. 본인인증이 되지 않은 유저일경우 <br>
     */
    @Override
    public void execute(ApplicationReqDto body, Long userId) {
        if (!userRepository.existsById(userId))
            throw new ExpectedException("존재하지 않는 유저입니다", HttpStatus.BAD_REQUEST);
        if (applicationRepository.existsByUserId(userId))
            throw new ExpectedException("원서가 이미 존재합니다", HttpStatus.BAD_REQUEST);
        Identity identity = identityRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("Identity가 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        IdentityDto identityDto = IdentityMapper.INSTANCE.identityToIdentityDto(identity);

        applicationRepository.save(
                ApplicationMapper.INSTANCE.applicationReqDtoAndIdentityDtoToApplication(body, identityDto, userId, null));
    }
}
