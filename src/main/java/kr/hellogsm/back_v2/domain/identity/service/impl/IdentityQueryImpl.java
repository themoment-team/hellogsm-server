package kr.hellogsm.back_v2.domain.identity.service.impl;

import kr.hellogsm.back_v2.domain.identity.dto.domain.IdentityDto;
import kr.hellogsm.back_v2.domain.identity.entity.Identity;
import kr.hellogsm.back_v2.domain.identity.repository.IdentityRepository;
import kr.hellogsm.back_v2.domain.identity.service.IdentityQuery;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * IdentityQuery의 기본 구현체입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Service
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
        return IdentityDto.from(identity);
    }
}
