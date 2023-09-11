package team.themoment.hellogsm.web.domain.identity.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.mapper.IdentityMapper;
import team.themoment.hellogsm.web.domain.identity.repository.IdentityRepository;
import team.themoment.hellogsm.web.domain.identity.service.IdentityQuery;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * IdentityQuery의 기본 구현체입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class IdentityQueryImpl implements IdentityQuery {

    private final IdentityRepository identityRepository;

    /**
     * userId를 사용하여 Identity를 조회합니다.
     *
     * @param userId 조회할 Identity의 userId
     * @return 조회한 Identity의 정보가 담긴 DTO
     * @throws ExpectedException 존재하지 않는 Identity일 경우 발생
     */
    @Override
    public IdentityDto execute(Long userId) {
        Identity identity = identityRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 Identity 입니다", HttpStatus.BAD_REQUEST));
        return IdentityMapper.INSTANCE.identityToIdentityDto(identity);
    }
}
