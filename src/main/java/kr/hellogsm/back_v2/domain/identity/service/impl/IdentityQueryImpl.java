package kr.hellogsm.back_v2.domain.identity.service.impl;

import kr.hellogsm.back_v2.domain.identity.dto.response.IdentityResDto;
import kr.hellogsm.back_v2.domain.identity.entity.Identity;
import kr.hellogsm.back_v2.domain.identity.repository.IdentityRepository;
import kr.hellogsm.back_v2.domain.identity.service.IdentityQuery;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityQueryImpl implements IdentityQuery {

    private final IdentityRepository identityRepository;

    @Override
    public IdentityResDto execute(Long userId) {
        Identity identity = identityRepository.findByUserId(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 Identity 입니다", HttpStatus.BAD_REQUEST));
        return IdentityResDto.from(identity);
    }
}
