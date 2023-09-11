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
 * 원서 생성을 위한 service interface 입니다
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class CreateApplicationServiceImpl implements CreateApplicationService {
    private final UserRepository userRepository;
    private final IdentityRepository identityRepository;
    private final ApplicationRepository applicationRepository;

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
